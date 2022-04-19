package com.TicTac.service;

import com.TicTac.exception.InvalidGameException;
import com.TicTac.exception.InvalidParamException;
import com.TicTac.exception.NotFoundException;
import com.TicTac.model.Game;
import com.TicTac.model.GamePlay;
import com.TicTac.model.Player;
import com.TicTac.model.TicToe;
import com.TicTac.storage.GameStorage;
import com.TicTac.model.GameStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Klasa GameService odpowiada za stworzenie planszy do gry oraz polaczenie graczy do jednej rozgrywki wraz z obsluga bledow oraz sama rozgrywke
 */
@Service
@AllArgsConstructor
public class GameService {
    /**
     *Funkcja tworzy plansze, ustawia status gry jako NEW oraz laczy pierwszego gracza do rozgrywki wraz z wygenerowaniem ID
     */
    public Game createGame(Player player) {
        Game game = new Game();
        game.setBoard(new int[3][3]);
        game.setGameId(UUID.randomUUID().toString());
        game.setPlayer1(player);
        game.setStatus(GameStatus.NEW);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    /**
     *Funkcja connectToGame pozwala graczowi numer dwa dolaczyc do rozgrywki oraz ustawia status gry na IN_PROGRESS.
     */

    public Game connectToGame(Player player2, String gameId) throws InvalidParamException, InvalidGameException {
        if (!GameStorage.getInstance().getGames().containsKey(gameId)) {
            throw new InvalidParamException("Game with provided id doesn't exist");
        }
        Game game = GameStorage.getInstance().getGames().get(gameId);

        if (game.getPlayer2() != null) {
            throw new InvalidGameException("Game is not valid anymore");
        }

        game.setPlayer2(player2);
        game.setStatus(GameStatus.IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    /**
     *Funkcja connectToRandomGame pozwala na połączenie drugiego gracza z już istniejaca gra.
     */
    public Game connectToRandomGame(Player player2) throws NotFoundException {
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it -> it.getStatus().equals(GameStatus.NEW))
                .findFirst().orElseThrow(() -> new NotFoundException("Game not found"));
        game.setPlayer2(player2);
        game.setStatus(GameStatus.IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    /**
     *Funkcja gamePlay sluzy do obslugi aktualnie trwajacej gry.
     * Pobiera wspolrzedne wprowadzanych znakow i sprawdza zwyciezce.
     */
    public Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException {
        if (!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId())) {
            throw new NotFoundException("Game not found");
        }

        Game game = GameStorage.getInstance().getGames().get(gamePlay.getGameId());
        if (game.getStatus().equals(GameStatus.FINISHED)) {
            throw new InvalidGameException("Game is already finished");
        }

        int[][] board = game.getBoard();
        board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()] = gamePlay.getType().getValue();

        Boolean xWinner = checkWinner(game.getBoard(), TicToe.X);
        Boolean oWinner = checkWinner(game.getBoard(), TicToe.O);

        if (xWinner) {
            game.setWinner(TicToe.X);
        } else if (oWinner) {
            game.setWinner(TicToe.O);
        }

        GameStorage.getInstance().setGame(game);
        return game;
    }

    /**
     * Funkcja checkWinner sprawdza czy na planszy pojawilo sie ustawienie X lub O  ktore pozwala na wygranie gry
     */
    private Boolean checkWinner(int[][] board, TicToe ticToe) {
        int[] boardArray = new int[9];
        int counterIndex = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boardArray[counterIndex] = board[i][j];
                counterIndex++;
            }
        }

        int[][] winCombinations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        for (int i = 0; i < winCombinations.length; i++) {
            int counter = 0;
            for (int j = 0; j < winCombinations[i].length; j++) {
                if (boardArray[winCombinations[i][j]] == ticToe.getValue()) {
                    counter++;
                    if (counter == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
