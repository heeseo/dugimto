package com.dugimto.service;

import com.dugimto.domain.*;
import com.dugimto.dto.BetDto;
import com.dugimto.exception.BetNotFoundException;
import com.dugimto.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final OutcomeService outcomeService;

    @Transactional
    public Long placeBet(Long userId, Long gameId, Long outcomeId, Long stake, MarketType marketType) {
        User user = userService.findOne(userId);
        Game game = gameService.findGameById(gameId);

        Outcome outcome = outcomeService.findOutcomeById(outcomeId);

        Bet bet = new Bet(user, game, stake, marketType, outcome.getName(), outcome.getPrice());
        betRepository.save(bet);
        return bet.getId();
    }

    @Transactional
    public void placeBets(List<BetDto> bets) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        bets.forEach(betDto -> {
            Game game = gameService.findGameById(betDto.getGameId());
            //odd validation to be implemented

            Bet bet = new Bet(user, game, betDto.getStake(), betDto.getMarketType(), betDto.getPrediction(), betDto.getOdds());
            betRepository.save(bet);
        });

    }

    public Bet findBetById(Long id) {
        return betRepository.findById(id).orElseThrow(() -> new BetNotFoundException(id));
    }

    public List<Bet> findAllBets() {
        return betRepository.findAll();
    }

}
