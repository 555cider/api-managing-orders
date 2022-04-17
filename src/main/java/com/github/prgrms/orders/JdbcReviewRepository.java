package com.github.prgrms.orders;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.prgrms.utils.DateTimeUtils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
public class JdbcReviewRepository implements ReviewRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcReviewRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<Review> findById(Long reviewSeq) {
		String sql = "SELECT * FROM reviews WHERE seq = ?";
		List<Review> reviewList = jdbcTemplate.query(sql, mapper, reviewSeq);
		return Optional.ofNullable(reviewList.isEmpty() ? null : reviewList.get(0));
	}

	@Override
	public Long review(Long userSeq, Long productSeq, String content) {
		String sql = "INSERT INTO reviews ( user_seq, product_seq, content ) VALUES ( ?, ?, ? )";
		PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(sql, Types.BIGINT,
				Types.BIGINT, Types.VARCHAR);
		pscFactory.setReturnGeneratedKeys(true);
		PreparedStatementCreator psc = pscFactory
				.newPreparedStatementCreator(Arrays.asList(userSeq, productSeq, content));
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		List<Map<String, Object>> keyList = keyHolder.getKeyList();
		if (ObjectUtils.isEmpty(keyList)) {
			return null;
		} else {
			Long reviewSeq = (Long) keyList.get(0).get("SEQ");
			return reviewSeq;
		}
	}

	static RowMapper<Review> mapper = (rs, rowNum) -> (new Review.Builder()
			.seq(rs.getLong("seq"))
			.userSeq(rs.getLong("user_seq"))
			.productSeq(rs.getLong("product_seq"))
			.content(rs.getString("content"))
			.createAt(DateTimeUtils.dateTimeOf(rs.getTimestamp("create_at")))
			.build());

}
