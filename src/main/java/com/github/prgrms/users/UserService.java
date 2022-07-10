package com.github.prgrms.users;

import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    public User login(Email email, String password);

    @Transactional(readOnly = true)
    public User findBySeq(Long userId);

}