package com.github.prgrms.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE users SET review_count = review_count + 1 WHERE seq = :seq", nativeQuery = true)
    int update(@Param("seq") Long seq);

}
