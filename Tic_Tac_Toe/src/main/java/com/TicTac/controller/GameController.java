package com.TicTac.controller;
/**
 *  @PostMapping - adnotacja mapujaca zadania HTTP POST na okreslone metody obslugi.
 *  @RequestMapping – adnotacja wskazujaca, ze dana metoda stanowi Endpoint.
 *  adnotacja ta moze byc wywolywana zdalnie. Przyjmuje ona wartosc stanowiaca relatywna sciezke dla jej wywołania
 *  @AllArgsConstructor - generuje konstruktor z jednym parametrem dla kazdego pola w klasie
 *  @RequestBody - Tresc zadania to dane wysylane przez klienta do interfejsu API. Tresc odpowiedzi to dane, ktore interfejs API wysyla do klienta
 *  @RestController - laczy @Controller i @ResponseBody, co eliminuje potrzebe dodawania adnotacji do kazdej metody obsługi zadan klasy kontrolera za pomocą adnotacji @ResponseBody.
 *  @Slf4j - umozliwia logowanie zdarzeń wystepujacych w trakcie dzialania aplikacji
 */

import com.TicTac.controller.dto.ConnectRequest;
import com.TicTac.exception.InvalidGameException;
import com.TicTac.exception.InvalidParamException;
import com.TicTac.exception.NotFoundException;
import com.TicTac.model.Game;
import com.TicTac.model.GamePlay;
import com.TicTac.model.Player;
import com.TicTac.service.GameService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Klasa ta zapewnia mozliwosc komunikacji z serwerem poprzez endpoint'y
 */

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
   private final SimpMessagingTemplate simpMessagingTemplate;
    /**
     *Stworzenie gry
     */
    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player) {
        log.info("start game request: {}", player);
        return ResponseEntity.ok(gameService.createGame(player));
    }
    /**
     *Polaczenie z gra
     */
    @PostMapping("/connect")
    public  ResponseEntity<Game> connect(@RequestBody ConnectRequest request) throws InvalidParamException, InvalidGameException {
        log.info("connect request: {}",request);
        return  ResponseEntity.ok(gameService.connectToGame(request.getPlayer(), request.getGameId()));

    }
    /**
     * Polaczenie z losową grą
     */
    @PostMapping("/connect/random")
    public ResponseEntity<Game> connectRandom(@RequestBody Player player) throws NotFoundException {
        log.info("connect random {}", player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }
    /**
     *Aktualna rozgrywka
     */
    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request) throws NotFoundException, InvalidGameException {
        log.info("gameplay: {}", request);
        Game game = gameService.gamePlay(request);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + game.getGameId(), game);
        return ResponseEntity.ok(game);
    }
}
