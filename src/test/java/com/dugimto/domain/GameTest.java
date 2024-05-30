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

        //act
        Game game = new Game(GameType.FOOTBALL, "EPL","Man utd", "Man city", LocalDateTime.now().plusHours(1));

        //assert
        assertThat(game).isNotNull();
        assertThat(game.getGameTitle()).isEqualTo("EPL");
        assertThat(game.getHomeTeam()).isEqualTo("Man utd");
        assertThat(game.getAwayTeam()).isEqualTo("Man city");
        assertThat(game.getGameType()).isEqualTo(GameType.FOOTBALL);
        assertThat(game.getGameStatus()).isEqualTo(GameStatus.CREATED);
        assertThat(game.getStartTime()).isEqualToIgnoringSeconds(LocalDateTime.now().plusHours(1)); // Adjusted for testability
        assertThat(game.getOddsEntries()).isEmpty();

    }

}