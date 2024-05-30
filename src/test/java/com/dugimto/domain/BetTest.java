package com.dugimto.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BetTest {

    @Test
    public void testBetCreation() throws Exception {
        //arrange
        User testUser = getTestUser();
        Game testGame = getGame();
        BetType testBetType = BetType.WIN;
        Long testAmount = 100L;

        // Initialize oddsEntry
        OddsEntry oddsEntry = new OddsEntry(MarketType.MATCH_RESULT, testGame);

        // Initialize outcomes
        Outcome outcome1 = new Outcome(oddsEntry, "home_team", 1.5);

        //act
        Bet bet = new Bet(testUser, testGame, testAmount, MarketType.MATCH_RESULT, outcome1.getName(), outcome1.getPrice());

        //assert
        assertThat(bet).isNotNull();
        assertThat(bet.getUser()).isEqualTo(testUser);
        assertThat(bet.getGame()).isEqualTo(testGame);
        assertThat(bet.getPrediction()).isEqualTo(outcome1.getName());
        assertThat(bet.getStake()).isEqualTo(testAmount);
        assertThat(bet.getOdds()).isEqualTo(1.5); // Check the odds corresponding to BetType
        assertThat(bet.isSettled()).isFalse();
        assertThat(bet.getCreatedAt()).isNull();
        assertThat(bet.getResult()).isEqualTo(Result.PENDING); // Assuming outcome is null initially

    }

    private static Game getGame() {
        Game testGame = new Game(GameType.FOOTBALL, "EPL","Man utd", "Man city", LocalDateTime.now().plusHours(1));
        return testGame;
    }

    private static User getTestUser() {
        return new User("testUser", "test@example.com", "password", UserRole.USER);
    }
}