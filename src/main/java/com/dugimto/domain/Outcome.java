package com.dugimto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outcome {

    @Id @GeneratedValue
    @Column(name = "outcome_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "odds_entry_id")
    private OddsEntry oddsEntry;

    private String name;  //user's prediction eg. home_team_name, draw

    private Double price; //odds for this outcome
    private Double line;

    public Outcome(OddsEntry oddsEntry, String name, Double price, Double line) {
        this.oddsEntry = oddsEntry;
        this.name = name;
        this.price = price;
        this.line = line;
        oddsEntry.addOutcome(this);
    }

    public Outcome(OddsEntry oddsEntry, String name, Double price) {
        this.oddsEntry = oddsEntry;
        this.name = name;
        this.price = price;
        oddsEntry.addOutcome(this);
    }

}
