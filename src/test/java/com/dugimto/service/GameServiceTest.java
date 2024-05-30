package com.dugimto.service;

import com.dugimto.domain.Game;
import com.dugimto.domain.GameType;
import com.dugimto.domain.MarketType;
import com.dugimto.dto.GameForm;
import com.dugimto.dto.OddsEntryDto;
import com.dugimto.dto.OutcomeDto;
import com.dugimto.exception.GameNotFoundException;
import com.dugimto.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GameServiceTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;
    @Test
    void createGame() {
        GameForm gameForm = getGameForm();
        Long gameId = gameService.createGame(gameForm);

        Optional<Game> gameFound = gameRepository.findById(gameId);

        assertThat(gameFound).isNotEmpty();
        assertThat(gameFound.get().getGameType()).isEqualTo(GameType.FOOTBALL);
        assertThat(gameFound.get().getGameTitle()).isEqualTo("GameTitle");
        assertThat(gameFound.get().getHomeTeam()).isEqualTo("home_team");
        assertThat(gameFound.get().getAwayTeam()).isEqualTo("away_team");
        assertThat(gameFound.get().getStartTime()).isEqualToIgnoringSeconds(LocalDateTime.now().plusHours(1));
        assertThat(gameFound.get().getOddsEntries()).isNotEmpty();
        assertOddsEntries(gameFound);

    }

    private static void assertOddsEntries(Optional<Game> gameFound) {
        assertThat(gameFound.get().getOddsEntries().size()).isEqualTo(1);
        assertThat(gameFound.get().getOddsEntries().get(0).getOutcomes()).isNotEmpty();
        assertThat(gameFound.get().getOddsEntries().get(0).getOutcomes().size()).isEqualTo(1);
        assertThat(gameFound.get().getOddsEntries().get(0).getOutcomes().get(0).getName()).isEqualTo("home_team");
        assertThat(gameFound.get().getOddsEntries().get(0).getOutcomes().get(0).getPrice()).isEqualTo(2.5);
    }

    private static GameForm getGameForm() {

        GameForm gameForm = new GameForm();
        gameForm.setGameTitle("GameTitle");
        gameForm.setHomeTeam("home_team");
        gameForm.setAwayTeam("away_team");
        gameForm.setGameType(GameType.FOOTBALL);
        gameForm.setStartTime(LocalDateTime.now().plusHours(1));

        List<OddsEntryDto> oddsEntryDtos = new ArrayList<>();
        OddsEntryDto oddsEntryDto = new OddsEntryDto();
        oddsEntryDto.setMarketType(MarketType.MATCH_RESULT);
        oddsEntryDtos.add(oddsEntryDto);

        List<OutcomeDto> outcomeDtos = new ArrayList<>();
        OutcomeDto outcomeDto = new OutcomeDto();
        outcomeDto.setName("home_team");
        outcomeDto.setPrice(2.5);
        outcomeDtos.add(outcomeDto);

        oddsEntryDto.setOutcomes(outcomeDtos);
        gameForm.setOddsEntries(oddsEntryDtos);

        return gameForm;
    }

    @Test
    void findAllGames() {
        Game game1 = new Game(GameType.FOOTBALL, "EPL","Man utd", "Man city", LocalDateTime.now().plusHours(1));
        Game game2 = new Game(GameType.FOOTBALL, "SerieA","Juv", "Mil", LocalDateTime.now().plusHours(1));

        gameRepository.save(game1);
        gameRepository.save(game2);

        List<Game> allGames = gameService.findAllGames();

        assertThat(allGames).isNotNull();
        assertThat(allGames).hasSize(2);
        assertThat(allGames).containsExactly(game1, game2);
    }

    @Test
    void findGameById() {
        Map<String, Double> oddsMap = new HashMap<>();
        oddsMap.put("win", 2.0);
        oddsMap.put("draw", 3.0);
        oddsMap.put("lose", 4.0);

        Game game1 = new Game(GameType.FOOTBALL, "EPL","Man utd", "Man city", LocalDateTime.now().plusHours(1));
        gameRepository.save(game1);


        assertThatNoException().isThrownBy(() -> gameService.findGameById(game1.getId()));
        Game foundGame = gameService.findGameById(game1.getId());
        assertThat(foundGame.getId()).isEqualTo(game1.getId());
        assertThat(foundGame.getGameTitle()).isEqualTo("EPL");
        assertThat(foundGame.getGameType()).isEqualTo(GameType.FOOTBALL);
        assertThat(foundGame.getHomeTeam()).isEqualTo("Man utd");
        assertThat(foundGame.getAwayTeam()).isEqualTo("Man city");
        assertThat(foundGame.getStartTime()).isEqualToIgnoringSeconds(LocalDateTime.now().plusHours(1));
        assertThat(foundGame.getOddsEntries()).isEmpty();

    }
}