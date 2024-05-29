package com.dugimto.mapper;

import com.dugimto.domain.Game;
import com.dugimto.domain.OddsEntry;
import com.dugimto.domain.Outcome;
import com.dugimto.dto.GameDto;
import com.dugimto.dto.GameForm;
import com.dugimto.dto.OddsEntryDto;
import com.dugimto.dto.OutcomeDto;

import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {

    public static Game mapToGame(GameForm gameForm) {
        Game game = new Game(
                gameForm.getGameType(),
                gameForm.getGameTitle(),
                gameForm.getHomeTeam(),
                gameForm.getAwayTeam(),
                gameForm.getStartTime());

        gameForm.getOddsEntries().stream()
                .map(oddsEntryDto -> mapToOddsEntry(oddsEntryDto, game))
                .collect(Collectors.toList());
        return game;
    }

    private static OddsEntry mapToOddsEntry(OddsEntryDto oddsEntryDto, Game game) {
        OddsEntry oddsEntry = new OddsEntry(oddsEntryDto.getMarketType(), game);

        oddsEntryDto.getOutcomes().stream()
                .map(outcomeDto -> mapToOutcome(outcomeDto, oddsEntry))
                .collect(Collectors.toList());
        return oddsEntry;
    }

    private static Outcome mapToOutcome(OutcomeDto outcomeDto, OddsEntry oddsEntry) {
        Outcome outcome = new Outcome(oddsEntry, outcomeDto.getName(), outcomeDto.getPrice());
        return outcome;
    }


    // Convert Game entity to GameDto
    public static GameDto toGameDto(Game game) {
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setGameType(game.getGameType());
        gameDto.setGameTitle(game.getGameTitle());
        gameDto.setHomeTeam(game.getHomeTeam());
        gameDto.setAwayTeam(game.getAwayTeam());
        gameDto.setStartTime(game.getStartTime());

        // Convert OddsEntry list to OddsEntryDto list
        List<OddsEntryDto> oddsEntryDtos = game.getOddsEntries().stream()
                .map(GameMapper::toOddsEntryDto)
                .collect(Collectors.toList());
        gameDto.setOddsEntries(oddsEntryDtos);

        return gameDto;
    }

    // Convert OddsEntry entity to OddsEntryDto
    public static OddsEntryDto toOddsEntryDto(OddsEntry oddsEntry) {
        OddsEntryDto oddsEntryDto = new OddsEntryDto();
        oddsEntryDto.setId(oddsEntry.getId());
        oddsEntryDto.setMarketType(oddsEntry.getMarketType());

        // Convert Outcome list to OutcomeDto list
        List<OutcomeDto> outcomeDtos = oddsEntry.getOutcomes().stream()
                .map(GameMapper::toOutcomeDto)
                .collect(Collectors.toList());
        oddsEntryDto.setOutcomes(outcomeDtos);

        return oddsEntryDto;
    }

    // Convert Outcome entity to OutcomeDto
    public static OutcomeDto toOutcomeDto(Outcome outcome) {
        OutcomeDto outcomeDto = new OutcomeDto();
        outcomeDto.setId(outcome.getId());
        outcomeDto.setName(outcome.getName());
        outcomeDto.setPrice(outcome.getPrice());
        return outcomeDto;
    }
}