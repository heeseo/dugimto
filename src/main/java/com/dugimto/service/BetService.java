package com.dugimto.service;

import com.dugimto.domain.Bet;
import com.dugimto.domain.BetType;
import com.dugimto.domain.Game;
import com.dugimto.domain.User;
import com.dugimto.exception.BetNotFoundException;
import com.dugimto.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BetService {

    private final BetRepository betRepository;
    private final UserService userService;
    private final GameService gameService;

    @Transactional
    public Long createBet(Long userId, Long gameId, BetType betType, Long amount) {
        User user = userService.findOne(userId);
        Game game = gameService.findGameById(gameId);
        Bet bet = new Bet(user, game, betType, amount);
        betRepository.save(bet);
        return bet.getId();
    }

    public Bet findBetById(Long id) {
        return betRepository.findById(id).orElseThrow(() -> new BetNotFoundException(id));
    }

    public List<Bet> findAllBets() {
        return betRepository.findAll();
    }
}
