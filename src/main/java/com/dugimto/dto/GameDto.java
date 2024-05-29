package com.dugimto.dto;

import com.dugimto.domain.Game;
import com.dugimto.domain.GameType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class GameDto {

    private Long id;
    private GameType gameType;
    private String gameTitle;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime startTime;
    private List<OddsEntryDto> oddsEntries;

    public GameDto(Game game) {
        id = game.getId();
        gameTitle = game.getGameTitle();
        homeTeam = game.getHomeTeam();
        awayTeam = game.getAwayTeam();
    }
}
