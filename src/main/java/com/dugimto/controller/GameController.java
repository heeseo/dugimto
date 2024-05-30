package com.dugimto.controller;

import com.dugimto.dto.GameForm;
import com.dugimto.service.GameService;
import com.dugimto.service.OddsApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameService gameService;
    private final OddsApiService oddsApiService;

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
        String odds = oddsApiService.fetchUpcomingAndLiveOdds();
        log.info("fetched odds: {}", odds);
        return "redirect:/";
    }
}
