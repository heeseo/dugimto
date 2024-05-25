package com.dugimto.controller;

import com.dugimto.dto.LoginRequest;
import com.dugimto.dto.SignUpRequest;
import com.dugimto.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login/login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signUpRequest", new SignUpRequest());
        return "signup/signup";
    }

    @PostMapping("/signup")
    public String addUser(SignUpRequest dto) {
        Long id = userService.register(dto);
        log.info("signup id: {}", id);
        return "redirect:/login";
    }
}
