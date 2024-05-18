package com.dugimto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bet {

    @Id @GeneratedValue
    @Column(name = "bet_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    private BetType betType;
    private Long amount;
    private double odds;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private boolean isSettled;

    @Enumerated(EnumType.STRING)
    private Outcome outcome = Outcome.PENDING;

    public Bet(User user, Game game, BetType betType, Long amount) {
        this.user = user;
        this.game = game;
        this.betType = betType;
        this.amount = amount;
        this.odds = game.getOddsMap().get(betType.toString());
    }
}
