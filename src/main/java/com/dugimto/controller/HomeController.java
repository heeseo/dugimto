package com.dugimto.controller;

import com.dugimto.domain.Game;
import com.dugimto.dto.GameDto;
import com.dugimto.mapper.GameMapper;
import com.dugimto.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GameService gameService;

    @GetMapping("/")
    public String home(Model model) {
        List<Game> games = gameService.findAllGames();
        List<GameDto> gameDtos = games.stream()
                .map(GameMapper::toGameDto)
                .collect(Collectors.toList());
        model.addAttribute("games", gameDtos);
        return "home";
    }
}
