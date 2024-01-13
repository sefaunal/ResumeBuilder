package com.sefaunal.resumebuilder.Service;

import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserByEmail(String userMail) {
        return userRepository.findByEmail(userMail)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with " + userMail));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with " + username));
    }

    public boolean isEmailFree(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    public boolean isUsernameFree(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }

    public void createUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole("USER");
        user.setAccountCreationDate(Instant.now());
        userRepository.save(user);
    }
}