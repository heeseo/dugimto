package com.dugimto.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bet> bets = new ArrayList<>();

    private Long points;
    private int winstreak;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.points = 0L;
        this.winstreak = 0;
    }
}
