package com.dugimto.service;

import com.dugimto.domain.*;
import com.dugimto.repository.BetRepository;
import com.dugimto.repository.GameRepository;
import com.dugimto.repository.OddsEntryRepository;
import com.dugimto.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
@Transactional
class BetServiceTest {

    @Autowired
    private BetService betService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private BetRepository betRepository;
    @Autowired
    private OddsEntryRepository oddsEntryRepository;

    private User testUser;
    private Game testGame;

    private OddsEntry oddsEntry;
    private List<Outcome> outcomes;


    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "testuser@example.com", "password", UserRole.USER);


        testGame = new Game(GameType.FOOTBALL, "EPL","Man utd", "Man city", LocalDateTime.now().plusHours(1));

        userRepository.save(testUser);
        gameRepository.save(testGame);

        // Initialize oddsEntry
        oddsEntry = new OddsEntry(MarketType.MATCH_RESULT, testGame);

        // Initialize outcomes
        Outcome outcome1 = new Outcome(oddsEntry, "home_team", 1.5);

        Outcome outcome2 = new Outcome(oddsEntry, "draw", 3.5);

        Outcome outcome3 = new Outcome(oddsEntry, "away_team", 2.5);

        oddsEntryRepository.save(oddsEntry);
    }

    @Test
    void createBet() {
        Long betId = betService.placeBet(testUser.getId(), testGame.getId(), oddsEntry.getOutcomes().get(0).getId(), 1000L, oddsEntry.getMarketType());

        assertThatNoException().isThrownBy(() -> betRepository.findById(betId));
        Bet bet = betRepository.findById(betId).get();

        assertThat(bet.getId()).isEqualTo(betId);
        assertThat(bet.getMarketType()).isEqualTo(MarketType.MATCH_RESULT);
        assertThat(bet.getPrediction()).isEqualTo(oddsEntry.getOutcomes().get(0).getName());
        assertThat(bet.getStake()).isEqualTo(1000L);
        assertThat(bet.getOdds()).isEqualTo(1.5);
        assertThat(bet.getResult()).isEqualTo(Result.PENDING);
        assertThat(bet.isSettled()).isFalse();
        assertThat(bet.getUser()).isEqualTo(testUser);
        assertThat(bet.getGame()).isEqualTo(testGame);
    }

    @Test
    void findBetById() {
        Bet bet = new Bet(testUser, testGame, 1000L, MarketType.MATCH_RESULT, "home_team", 2.5);
        betRepository.save(bet);

        Bet foundBet = betService.findBetById(bet.getId());

        assertThat(foundBet).isEqualTo(bet);
        assertThat(foundBet.getPrediction()).isEqualTo("home_team");
        assertThat(foundBet.getStake()).isEqualTo(1000L);
        assertThat(foundBet.getOdds()).isEqualTo(2.5);
        assertThat(foundBet.getResult()).isEqualTo(Result.PENDING);
        assertThat(foundBet.isSettled()).isFalse();
        assertThat(foundBet.getUser()).isEqualTo(testUser);
        assertThat(foundBet.getGame()).isEqualTo(testGame);
    }

    @Test
    void findAllBets() {
        Bet bet = new Bet(testUser, testGame, 1000L, MarketType.MATCH_RESULT, "home_team", 2.5);
        betRepository.save(bet);

        List<Bet> bets = betService.findAllBets();

        assertThat(bets).isNotEmpty();
        assertThat(bets.size()).isEqualTo(1);
        assertThat(bets).containsExactly(bet);
    }
}