package com.dugimto.dto;

import com.dugimto.domain.GameType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class GameForm {

    private GameType gameType;
    private String gameTitle;
    private String homeTeam;
    private String awayTeam;
    private LocalDateTime startTime;
    private List<OddsEntryDto> OddsEntries;

}
