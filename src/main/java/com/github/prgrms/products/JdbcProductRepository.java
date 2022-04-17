package com.github.prgrms.products;

import static com.github.prgrms.utils.DateTimeUtils.dateTimeOf;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM products ORDER BY seq DESC LIMIT ? OFFSET ?",
                mapper, pageable.getSize(), pageable.getOffset());
    }

    @Override
    public Optional<Product> findById(Long productSeq) {
        List<Product> productList = jdbcTemplate.query("SELECT * FROM products WHERE seq = ?", mapper, productSeq);
        return Optional.ofNullable(productList.isEmpty() ? null : productList.get(0));
    }

    @Override
    public int addReviewCount(Long productSeq) {
        return jdbcTemplate.update(
                "UPDATE products SET review_count = review_count + 1 WHERE seq = ?", productSeq);
    }

    static RowMapper<Product> mapper = (rs, rowNum) -> new Product.Builder()
            .seq(rs.getLong("seq"))
            .name(rs.getString("name"))
            .details(rs.getString("details"))
            .reviewCount(rs.getInt("review_count"))
            .createAt(dateTimeOf(rs.getTimestamp("create_at")))
            .build();

}
