package com.dugimto.controller;

import com.dugimto.domain.Game;
import com.dugimto.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GameService gameService;

    @GetMapping("/")
    public String home(Model model) {
        List<Game> games = gameService.findAllGames();
        model.addAttribute("games", games);
        return "home";
    }
}
