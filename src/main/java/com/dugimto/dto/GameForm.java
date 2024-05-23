package com.dugimto.dto;

import com.dugimto.domain.Game;
import com.dugimto.domain.GameType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class GameForm {

    private String detail;
    private GameType gameType;
    private LocalDateTime startTime;
    private Map<String, Double> oddsMap;

    public Game toGame() {
       return new Game(detail, gameType, startTime, oddsMap);
    }
}
