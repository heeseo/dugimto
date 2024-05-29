package com.dugimto.controller;

import com.dugimto.domain.Game;
import com.dugimto.domain.GameType;
import com.dugimto.dto.GameForm;
import com.dugimto.dto.OddsEntryDto;
import com.dugimto.dto.OutcomeDto;
import com.dugimto.service.GameService;
import com.dugimto.service.OddsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameService gameService;
    private final OddsService oddsService;

    @GetMapping("/games/new")
    public String createGameForm(Model model) {
        model.addAttribute("gameForm", new GameForm());
        return "games/createGameForm";
    }

    @PostMapping("/games/new")
    public String createGame(GameForm gameForm, HttpServletRequest request) {
        log.info("gameForm: {}", gameForm);
        gameService.createGame(gameForm);

        return "redirect:/";
    }

    @GetMapping("/games/new/odds")
    public String getOdds(Model model) {
        String odds = oddsService.fetchUpcomingAndLiveOdds();
        log.info("fetched odds: {}", odds);
        return "redirect:/";
    }
}
