package com.dugimto.service;

import com.dugimto.domain.User;
import com.dugimto.dto.SignUpRequest;
import com.dugimto.exception.UserNotFoundException;
import com.dugimto.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testuser", "testuser@example.com", "password");
    }

    @Test
    void register() {
        //arrange
        SignUpRequest dto = new SignUpRequest();
        dto.setEmail("testuser@example.com");
        dto.setPassword("password");
        dto.setUsername("testuser");

        Long userId = userService.register(dto);

        //act
        User foundUser = userRepository.findById(userId).orElse(null);

        //assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(dto.getEmail());
        assertThat(foundUser.getUsername()).isEqualTo(dto.getUsername());
        assertThat(foundUser.getPassword()).isNotEqualTo(dto.getPassword()); //encrypted
        assertThat(foundUser.getId()).isEqualTo(userId);
    }

    @Test
    void findUsers() {
        //arrange
        userRepository.save(user);

        //act
        List<User> users = userService.findUsers();

        //assert
        assertThat(users.size()).isEqualTo(1);
        assertThat(users).containsExactly(user);
    }

    @Test
    public void findOne() {
        //arrange
        userRepository.save(user);

        //act
        User foundUser = userService.findOne(user.getId());

        //assert
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser).isEqualTo(user);
    }

    @Test
    public void findNonExistingUser() throws Exception {
        Long nonExistingUserId = 2323L;

        Optional<User> userFound = userRepository.findById(nonExistingUserId);

        assertThat(userFound).isEmpty();
        assertThatThrownBy(() -> userService.findOne(nonExistingUserId))
                .isInstanceOf(UserNotFoundException.class);
    }

}