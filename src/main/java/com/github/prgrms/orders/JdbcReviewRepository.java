package com.github.prgrms.orders;

import java.sql.PreparedStatement;
import java.util.List;

import com.github.prgrms.utils.DateTimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcReviewRepository implements ReviewRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Review findById(Long reviewSeq) {
		String sql = "SELECT * FROM reviews WHERE seq = ?";
		List<Review> reviewList = jdbcTemplate.query(sql, mapper, reviewSeq);
		return reviewList.isEmpty() ? null : reviewList.get(0);
	}

	@Override
	public Long review(Long userSeq, Long productSeq, String content) {
		String sql = "INSERT INTO reviews ( user_seq, product_seq, content ) VALUES ( ?, ?, ? )";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, userSeq);
			ps.setLong(2, productSeq);
			ps.setString(3, content);
			return ps;
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	static RowMapper<Review> mapper = (rs, rowNum) -> (new Review.Builder()
			.seq(rs.getLong("seq"))
			.userSeq(rs.getLong("user_seq"))
			.productSeq(rs.getLong("product_seq"))
			.content(rs.getString("content"))
			.createAt(DateTimeUtils.dateTimeOf(rs.getTimestamp("create_at")))
			.build());

}
