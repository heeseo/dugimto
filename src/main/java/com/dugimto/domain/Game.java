package com.dugimto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

    @Id @GeneratedValue
    @Column(name = "game_id")
    private Long id;

    private String detail;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Bet> bets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;
    private LocalDateTime startTime;

    @ElementCollection
    @CollectionTable(name = "game_odds", joinColumns = @JoinColumn(name = "game_id"))
    @MapKeyColumn(name = "bet_type")
    @Column(name = "odds")
    private Map<String, Double> oddsMap = new HashMap<>();

    public Game(String detail, GameType gameType, LocalDateTime startTime, Map<String, Double> oddsMap) {
        this.detail = detail;
        this.gameType = gameType;
        this.gameStatus = GameStatus.CREATED;
        this.startTime = startTime;
        this.oddsMap = oddsMap;
    }
}
