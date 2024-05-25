package com.dugimto.service;

import com.dugimto.domain.User;
import com.dugimto.domain.UserRole;
import com.dugimto.dto.SignUpRequest;
import com.dugimto.exception.EmailAlreadyExistsException;
import com.dugimto.exception.UserNotFoundException;
import com.dugimto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Long register(SignUpRequest dto) {
        validateDuplicateEmail(dto);

        return userRepository.save(User.builder()
                    .email(dto.getEmail())
                    .username(dto.getUsername())
                    .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                    .role(UserRole.USER)
                    .build()).getId();
    }

    private void validateDuplicateEmail(SignUpRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException(dto.getEmail());
        }
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
