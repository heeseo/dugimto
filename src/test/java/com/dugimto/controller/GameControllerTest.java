package com.dugimto.controller;

import com.dugimto.config.WebSecurityConfig;
import com.dugimto.domain.Game;
import com.dugimto.domain.GameType;
import com.dugimto.dto.GameForm;
import com.dugimto.service.GameService;
import com.dugimto.service.UserDetailService;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
@Import(WebSecurityConfig.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserDetailService userDetailService;
    @MockBean
    private GameService gameService;

    @Test
    @DisplayName("ShouldReturnCreateGameForm")
    void createGameForm() throws Exception {
        mockMvc.perform(get("/games/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("games/createGameForm"))
                .andExpect(model().attributeExists("gameForm"));
    }

    @Test
    @DisplayName("ShouldRedirectToHome")
    void createGame() throws Exception {
        GameForm gameForm = getGameForm();

        mockMvc.perform(post("/games/new")
                        .with(csrf())
                        .flashAttr("gameForm", gameForm))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Verify that the service was called
        verify(gameService, times(1)).createGame(any(GameForm.class));
    }

    private static GameForm getGameForm() {
        GameForm gameForm = new GameForm();
        gameForm.setDetail("MCI vs WHU");
        gameForm.setGameType(GameType.FOOTBALL);
        gameForm.setStartTime(LocalDateTime.now().plusHours(1));
        Map<String, Double> oddsMap = new HashMap<>();
        oddsMap.put("win", 2.0);
        oddsMap.put("draw", 3.0);
        oddsMap.put("lose", 4.0);
        gameForm.setOddsMap(oddsMap);
        return gameForm;
    }
}