package com.dugimto.service;

import com.dugimto.domain.*;
import com.dugimto.repository.BetRepository;
import com.dugimto.repository.GameRepository;
import com.dugimto.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

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

    private User testUser;
    private Game testGame;

    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "testuser@example.com", "password");

        Map<String, Double> oddsMap = new HashMap<>();
        oddsMap.put("WIN", 2.0); // Odds for winning
        oddsMap.put("DRAW", 3.0); // Odds for draw
        oddsMap.put("LOSE", 4.0); // Odds for losing

        testGame = new Game("test game", GameType.FOOTBALL, LocalDateTime.now().plusHours(1), oddsMap);

        userRepository.save(testUser);
        gameRepository.save(testGame);
    }

    @Test
    void createBet() {
        Long betId = betService.createBet(testUser.getId(), testGame.getId(), BetType.WIN, 1000L);

        assertThatNoException().isThrownBy(() -> betRepository.findById(betId));
        Bet bet = betRepository.findById(betId).get();

        assertThat(bet.getId()).isEqualTo(betId);
        assertThat(bet.getBetType()).isEqualTo(BetType.WIN);
        assertThat(bet.getAmount()).isEqualTo(1000L);
        assertThat(bet.getOdds()).isEqualTo(2.0);
        assertThat(bet.getOutcome()).isEqualTo(Outcome.PENDING);
        assertThat(bet.isSettled()).isFalse();
        assertThat(bet.getUser()).isEqualTo(testUser);
        assertThat(bet.getGame()).isEqualTo(testGame);
    }

    @Test
    void findBetById() {
        Bet bet = new Bet(testUser, testGame, BetType.WIN, 1000L);
        betRepository.save(bet);

        Bet foundBet = betService.findBetById(bet.getId());

        assertThat(foundBet).isEqualTo(bet);
        assertThat(foundBet.getBetType()).isEqualTo(BetType.WIN);
        assertThat(foundBet.getAmount()).isEqualTo(1000L);
        assertThat(foundBet.getOdds()).isEqualTo(2.0);
        assertThat(foundBet.getOutcome()).isEqualTo(Outcome.PENDING);
        assertThat(foundBet.isSettled()).isFalse();
        assertThat(foundBet.getUser()).isEqualTo(testUser);
        assertThat(foundBet.getGame()).isEqualTo(testGame);
    }

    @Test
    void findAllBets() {
        Bet bet = new Bet(testUser, testGame, BetType.WIN, 1000L);
        betRepository.save(bet);

        List<Bet> bets = betService.findAllBets();

        assertThat(bets).isNotEmpty();
        assertThat(bets.size()).isEqualTo(1);
        assertThat(bets).containsExactly(bet);
    }
}