package com.dugimto.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("testuser", "test@example.com", "password123", UserRole.USER);
    }

    @Test
    public void testUserCreation() {
        //assert
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getPoints()).isEqualTo(0L);
        assertThat(user.getWinstreak()).isEqualTo(0);
        assertThat(user.getBets()).isNotNull();
        assertThat(user.getBets().size()).isEqualTo(0);
    }
}