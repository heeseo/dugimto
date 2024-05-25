package com.dugimto.service;

import com.dugimto.domain.Game;
import com.dugimto.domain.GameType;
import com.dugimto.dto.GameForm;
import com.dugimto.exception.GameNotFoundException;
import com.dugimto.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        assertThat(gameFound.get().getDetail()).isEqualTo("test game");
        assertThat(gameFound.get().getGameType()).isEqualTo(GameType.FOOTBALL);
        assertThat(gameFound.get().getStartTime()).isEqualToIgnoringSeconds(LocalDateTime.now().plusHours(1));
        assertThat(gameFound.get().getOddsMap()).containsEntry("win", 2.0); // Check odds for winning
        assertThat(gameFound.get().getOddsMap()).containsEntry("draw", 3.0); // Check odds for draw
        assertThat(gameFound.get().getOddsMap()).containsEntry("lose", 4.0); // Check odds for losing
    }

    private static GameForm getGameForm() {
        Map<String, Double> oddsMap = new HashMap<>();
        oddsMap.put("win", 2.0); // Odds for winning
        oddsMap.put("draw", 3.0); // Odds for draw
        oddsMap.put("lose", 4.0); // Odds for losing

        GameForm gameForm = new GameForm();
        gameForm.setDetail("test game");
        gameForm.setGameType(GameType.FOOTBALL);
        gameForm.setStartTime(LocalDateTime.now().plusHours(1));
        gameForm.setOddsMap(oddsMap);
        return gameForm;
    }

    @Test
    void findAllGames() {
        Map<String, Double> oddsMap = new HashMap<>();
        oddsMap.put("win", 2.0);
        oddsMap.put("draw", 3.0);
        oddsMap.put("lose", 4.0);

        Game game1 = new Game("test game1", GameType.FOOTBALL, LocalDateTime.now().plusHours(1), oddsMap);
        Game game2 = new Game("test game2", GameType.BASKETBALL, LocalDateTime.now().plusHours(2), oddsMap);

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

        Game game1 = new Game("test game1", GameType.FOOTBALL, LocalDateTime.now().plusHours(1), oddsMap);
        gameRepository.save(game1);


        assertThatNoException().isThrownBy(() -> gameService.findGameById(game1.getId()));
        Game foundGame = gameService.findGameById(game1.getId());
        assertThat(foundGame.getId()).isEqualTo(game1.getId());
        assertThat(foundGame.getDetail()).isEqualTo("test game1");
        assertThat(foundGame.getGameType()).isEqualTo(GameType.FOOTBALL);
        assertThat(foundGame.getStartTime()).isEqualToIgnoringSeconds(LocalDateTime.now().plusHours(1));
        assertThat(foundGame.getOddsMap()).containsEntry("win", 2.0); // Check odds for winning
        assertThat(foundGame.getOddsMap()).containsEntry("draw", 3.0); // Check odds for draw
        assertThat(foundGame.getOddsMap()).containsEntry("lose", 4.0); // Check odds for losing
    }
}