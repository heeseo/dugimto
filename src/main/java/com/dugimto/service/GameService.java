package com.dugimto.service;

import com.dugimto.domain.Game;
import com.dugimto.domain.GameType;
import com.dugimto.dto.GameForm;
import com.dugimto.exception.GameNotFoundException;
import com.dugimto.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;

    @Transactional
    public Long createGame(GameForm gameForm) {
        Game game = gameForm.toGame();
        gameRepository.save(game);
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
