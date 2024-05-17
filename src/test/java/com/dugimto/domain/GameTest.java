package com.dugimto.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void testGameCreation() throws Exception {
        //arrange
        Map<String, Double> oddsMap = new HashMap<>();
        oddsMap.put("win", 2.0); // Odds for winning
        oddsMap.put("draw", 3.0); // Odds for draw
        oddsMap.put("lose", 4.0); // Odds for losing

        //act
        Game game = new Game("MCI vs WHU", GameType.FOOTBALL, LocalDateTime.now().plusHours(1), oddsMap);

        //assert
        assertThat(game).isNotNull();
        assertThat(game.getDetail()).isEqualTo("MCI vs WHU");
        assertThat(game.getGameType()).isEqualTo(GameType.FOOTBALL);
        assertThat(game.getStartTime()).isEqualToIgnoringSeconds(LocalDateTime.now().plusHours(1)); // Adjusted for testability
        assertThat(game.getOddsMap()).containsEntry("win", 2.0); // Check odds for winning
        assertThat(game.getOddsMap()).containsEntry("draw", 3.0); // Check odds for draw
        assertThat(game.getOddsMap()).containsEntry("lose", 4.0); // Check odds for losing
    }

}