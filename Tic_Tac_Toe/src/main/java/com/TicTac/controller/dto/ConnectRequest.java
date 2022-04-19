package com.TicTac.controller.dto;

import com.TicTac.model.Player;
import lombok.Data;
/**
 * Klasa ConnectRequest odpowiada za polaczenie dwoch graczy do rozgrywki
 * Jest ona wykorzystywana w klasie GameController
 */

@Data
public class ConnectRequest {
    private Player player;
    private String gameId;
}
