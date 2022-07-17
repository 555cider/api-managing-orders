package com.github.prgrms.users;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User login(Email email, String password) {
        Preconditions.checkNotNull(email, "email must be provided");
        Preconditions.checkNotNull(password, "password must be provided");

        User user = this.userRepository.findByEmail(email.getAddress());

        user.login(this.passwordEncoder, password);
        user.afterLoginSuccess();
        userRepository.update(user.getSeq());

        return user;
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long seq) {
        Preconditions.checkNotNull(seq, "userId must be provided");

        Optional<User> user = userRepository.findById(seq);
        Preconditions.checkArgument(user.isPresent(), "No user was found");

        return new UserDto(user.get());
    }

}