package com.TicTac.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  Enum to typ wyliczeniowy, ktory umozliwia w Javie zadeklarowanie ograniczonej liczby mozliwych wartosci
 *  Przypisanie wartosci odpowiednim znakom, w tym wypadku X=1, O=2
 */
@AllArgsConstructor
@Getter
public enum TicToe {
    X(1), O(2);

    private Integer value;
}
