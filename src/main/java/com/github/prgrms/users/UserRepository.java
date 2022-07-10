package com.github.prgrms.users;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    User findBySeq(Long seq);

    User findByEmail(Email email);

    @Modifying
    @Query(value = "UPDATE users SET review_count = review_count + 1 WHERE seq = :seq", nativeQuery = true)
    int update(@Param("seq") Long seq);

}
