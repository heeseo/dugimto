package com.dugimto.service;

import com.dugimto.domain.User;
import com.dugimto.exception.UserNotFoundException;
import com.dugimto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long register(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
