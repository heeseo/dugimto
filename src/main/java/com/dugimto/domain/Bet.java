package com.dugimto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    private MarketType marketType;

    private String prediction;
    private Long stake;
    private Double odds;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private boolean isSettled;

    @Enumerated(EnumType.STRING)
    private Result result = Result.PENDING;

    public Bet(User user, Game game, Long stake, MarketType marketType, Outcome outcome) {
        this.user = user;
        this.game = game;
        this.marketType = marketType;
        this.stake = stake;
        this.prediction = outcome.getName();
        this.odds = outcome.getPrice();
    }
}
