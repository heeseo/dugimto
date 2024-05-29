package com.dugimto.controller;

import com.dugimto.domain.Bet;
import com.dugimto.dto.BetDto;
import com.dugimto.service.BetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BetController {

    private final BetService betService;

    @PostMapping("/bets/new")
    public ResponseEntity<String> placeBets(@RequestBody List<BetDto> bets) {
        try {

            log.info("betDtos: {}", bets);
            betService.placeBets(bets);

            return ResponseEntity.ok("Bets placed successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error placing bets: " + e.getMessage());
        }
    }
}
