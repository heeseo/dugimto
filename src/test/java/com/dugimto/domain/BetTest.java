package com.dugimto.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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

        //act
        Bet bet = new Bet(testUser, testGame, testBetType, testAmount);

        //assert
        assertThat(bet).isNotNull();
        assertThat(bet.getUser()).isEqualTo(testUser);
        assertThat(bet.getGame()).isEqualTo(testGame);
        assertThat(bet.getBetType()).isEqualTo(testBetType);
        assertThat(bet.getAmount()).isEqualTo(testAmount);
        assertThat(bet.getOdds()).isEqualTo(2.0); // Check the odds corresponding to BetType
        assertThat(bet.isSettled()).isFalse();
        assertThat(bet.getCreatedAt()).isNull();
        assertThat(bet.getOutcome()).isEqualTo(Outcome.PENDING); // Assuming outcome is null initially

    }

    private static Game getGame() {
        Map<String, Double> oddsMap = new HashMap<>();
        oddsMap.put("WIN", 2.0);
        oddsMap.put("DRAW", 3.0);
        oddsMap.put("LOSE", 4.0);

        Game testGame = new Game("MCI vs WHU", GameType.FOOTBALL, LocalDateTime.now().plusHours(1), oddsMap);
        return testGame;
    }

    private static User getTestUser() {
        return new User("testUser", "test@example.com", "password");
    }
}