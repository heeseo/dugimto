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

    @Enumerated(EnumType.STRING)
    private GameType gameType; //sport
    private String gameTitle; //league
    private String homeTeam;
    private String awayTeam;
    private String detail;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Bet> bets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;
    private LocalDateTime startTime;

//    @ElementCollection
//    @CollectionTable(name = "game_odds", joinColumns = @JoinColumn(name = "game_id"))
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<OddsEntry> oddsEntries = new ArrayList<>();

    public Game(GameType gameType, String gameTitle, String homeTeam, String awayTeam, LocalDateTime startTime) {
        this.gameType = gameType;
        this.gameTitle = gameTitle;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameStatus = GameStatus.CREATED;
        this.startTime = startTime;
    }

    public void addOddsEntry(OddsEntry oddsEntry) {
        this.oddsEntries.add(oddsEntry);
    }
}
