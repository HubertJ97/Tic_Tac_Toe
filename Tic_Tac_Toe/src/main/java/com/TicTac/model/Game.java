package com.TicTac.model;

import lombok.Data;

/**
 * @Data generuje gettery, settery, metody toString oraz hashCode
 * Klasa przechowuje ID gry, nazwe obydwu graczy, status gry, plansze oraz zwyciezce
 */
@Data
public class Game {

    private String gameId;
    private Player player1;
    private Player player2;
    private GameStatus status;
    private int[][] board;
    private TicToe winner;

}
