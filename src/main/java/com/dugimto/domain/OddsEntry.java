package com.dugimto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OddsEntry {

    @Id @GeneratedValue
    @Column(name = "odds_entry_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MarketType marketType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "oddsEntry", cascade = CascadeType.ALL)
    private List<Outcome> outcomes = new ArrayList<>();

    public OddsEntry(MarketType marketType, Game game) {
        this.marketType = marketType;
        this.game = game;
        game.addOddsEntry(this);
    }

    public void addOutcome(Outcome outcome) {
        this.outcomes.add(outcome);
    }
}
