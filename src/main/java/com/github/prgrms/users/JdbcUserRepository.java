package com.github.prgrms.users;

import static com.github.prgrms.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> results = jdbcTemplate.query("SELECT * FROM users WHERE seq=?", mapper, id);
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        List<User> results = jdbcTemplate.query("SELECT * FROM users WHERE email=?", mapper, email.getAddress());
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE users SET passwd=?,login_count=?,last_login_at=? WHERE seq=?", user.getPasswd(),
                user.getLoginCount(), user.getLastLoginAt().orElse(null), user.getSeq());
    }

    static RowMapper<User> mapper = (rs, rowNum) -> new User.Builder()
            .seq(rs.getLong("seq"))
            .name(rs.getString("name"))
            .email(new Email(rs.getString("email")))
            .passwd(rs.getString("passwd"))
            .loginCount(rs.getInt("login_count"))
            .lastLoginAt(dateTimeOf(rs.getTimestamp("last_login_at")))
            .createAt(dateTimeOf(rs.getTimestamp("create_at")))
            .build();

}
