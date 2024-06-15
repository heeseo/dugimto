package com.dugimto.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "external_id", unique = true)
    private String externalId;

    @Enumerated(EnumType.STRING)
    private GameType gameType; //sport
    private String gameTitle; //league
    private String homeTeam;
    private String awayTeam;
    private String detail;

    @OneToMany(mappedBy = "game")
    private List<Bet> bets = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;
    private LocalDateTime startTime;

//    @ElementCollection
//    @CollectionTable(name = "game_odds", joinColumns = @JoinColumn(name = "game_id"))
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OddsEntry> oddsEntries = new ArrayList<>();

    @Builder
    public Game(GameType gameType, String gameTitle, String homeTeam, String awayTeam, LocalDateTime startTime, String externalId) {
        this.gameType = gameType;
        this.gameTitle = gameTitle;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.gameStatus = GameStatus.CREATED;
        this.startTime = startTime;
        this.externalId = externalId;
    }
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

    public void updateOddsEntries(List<OddsEntry> oddsEntries) {
        this.oddsEntries.clear();
        this.oddsEntries.addAll(oddsEntries);
        oddsEntries.forEach(oddsEntry -> oddsEntry.updateGame(this));
    }
}
