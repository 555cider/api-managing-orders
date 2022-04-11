package com.github.prgrms.users;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.prgrms.errors.NotFoundException;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public User login(Email email, String password) {
        checkNotNull(password, "password must be provided");
        User user = this.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Could not found user for " + email));
        user.login(this.passwordEncoder, password);
        user.afterLoginSuccess();
        this.userRepository.update(user);
        return user;
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        checkNotNull(userId, "userId must be provided");
        return this.userRepository.findById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(Email email) {
        checkNotNull(email, "email must be provided");
        return this.userRepository.findByEmail(email);
    }

}