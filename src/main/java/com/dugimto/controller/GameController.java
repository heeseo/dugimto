package com.dugimto.controller;

import com.dugimto.domain.Game;
import com.dugimto.dto.GameForm;
import com.dugimto.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/games/new")
    public String createGameForm(Model model) {
        model.addAttribute("gameForm", new GameForm());
        return "games/createGameForm";
    }

    @PostMapping("/games/new")
    public String createGame(GameForm gameForm) {
        Game game = gameForm.toGame();
        gameService.createGame(game);
        return "redirect:/";
    }
}
