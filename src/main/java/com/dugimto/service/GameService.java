package com.dugimto.service;

import com.dugimto.domain.Game;
import com.dugimto.domain.GameType;
import com.dugimto.dto.GameForm;
import com.dugimto.exception.GameNotFoundException;
import com.dugimto.mapper.GameMapper;
import com.dugimto.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GameService {

    private final GameRepository gameRepository;

    @Transactional
    public Long createGame(GameForm gameForm) {
        Game game = GameMapper.mapToGame(gameForm);
        gameRepository.save(game);
        log.info("game: {}", game);
        return game.getId();
    }

    public List<Game> findAllGames() {
        List<Game> games = gameRepository.findAll();
        return games;
    }

    public Game findGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
    }
}
