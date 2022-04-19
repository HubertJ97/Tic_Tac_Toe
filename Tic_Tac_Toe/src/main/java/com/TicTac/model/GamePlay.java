package com.TicTac.model;

import lombok.Data;

/**
 * Klasa ta przechowuje typ znaku jaki zostaje wstawiony przez danego gracza (X lub O) oraz wspolrzedne owych znakow (w którym miejscu na planszy zostaly umieszczone) w danej grze
 * przechowuje rowniez ID gry
 */
@Data
public class GamePlay {

    private TicToe type;
    private Integer coordinateX;
    private Integer coordinateY;
    private String gameId;
}
