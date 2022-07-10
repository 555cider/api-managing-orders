package com.github.prgrms.users;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User login(Email email, String password) {
        checkNotNull(password, "password must be provided");
        User user = this.findByEmail(email);
        user.login(this.passwordEncoder, password);
        user.afterLoginSuccess();
        userRepository.update(user.getSeq());
        return user;
    }

    @Transactional(readOnly = true)
    public User findBySeq(Long seq) {
        checkNotNull(seq, "userId must be provided");
        return userRepository.findBySeq(seq);
    }

    @Transactional(readOnly = true)
    public User findByEmail(Email email) {
        checkNotNull(email, "email must be provided");
        return userRepository.findByEmail(email);
    }

}