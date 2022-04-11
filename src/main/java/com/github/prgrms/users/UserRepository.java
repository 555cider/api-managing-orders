package com.github.prgrms.users;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(Email email);

    void update(User user);

}
