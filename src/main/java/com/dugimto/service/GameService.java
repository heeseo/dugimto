package com.dugimto.service;

import com.dugimto.domain.Game;
import com.dugimto.dto.GameForm;
import com.dugimto.exception.GameNotFoundException;
import com.dugimto.mapper.GameMapper;
import com.dugimto.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GameService {

    private final GameRepository gameRepository;
    private final OddsApiService oddsApiService;

    @Transactional
    public Long createGame(GameForm gameForm) {
        Game game = GameMapper.mapToGame(gameForm);
        gameRepository.save(game);
        log.info("game: {}", game);
        return game.getId();
    }

    @Transactional
    public int createGames(List<Game> games) {
        List<Game> savedGames = gameRepository.saveAll(games);
        return savedGames.size();
    }

    public List<Game> findAllGames() {
        List<Game> games = gameRepository.findAll();
        return games;
    }

    public Game findGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException(id));
    }

    @Transactional
    public int fetchAndSaveGamesFromOddsApiV2() {
        List<Game> games = oddsApiService.fetchUpcomingAndLiveOdds();
        return createGames(games);
    }

    @Transactional
    public int fetchAndSaveGamesFromOddsApi() {
        List<Game> fetchedGames = oddsApiService.fetchUpcomingAndLiveOdds().stream()
                .filter(game -> game.getOddsEntries() != null && !game.getOddsEntries().isEmpty())
                .collect(Collectors.toList());

        Map<String, Game> existingGamesMap = gameRepository.findAllByExternalIdIn(
                fetchedGames.stream().map(Game::getExternalId).collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(Game::getExternalId, Function.identity()));

        List<Game> gamesToSave = new ArrayList<>();

        for (Game fetchedGame : fetchedGames) {
            if (existingGamesMap.containsKey(fetchedGame.getExternalId())) {
                Game existingGame = existingGamesMap.get(fetchedGame.getExternalId());
                updateExistingGame(existingGame, fetchedGame);
                gamesToSave.add(existingGame);
            } else {
                gamesToSave.add(fetchedGame);
            }
        }

        log.info("games to save: {}", gamesToSave.size());
        gameRepository.saveAll(gamesToSave);
        return fetchedGames.size();
    }
    private void updateExistingGame(Game existingGame, Game fetchedGame) {
        existingGame.updateOddsEntries(fetchedGame.getOddsEntries());
    }
}
