package com.github.prgrms.orders;

import java.util.List;

import com.github.prgrms.configures.web.Pageable;
import com.github.prgrms.utils.Const;
import com.github.prgrms.utils.DateTimeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOrderRepository implements OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Order> findAll(Pageable pageable) {
        String sql = new StringBuilder()
                .append("SELECT *, r.seq AS review_seq, r.create_at AS review_create_at ")
                .append("FROM orders AS o ")
                .append("LEFT OUTER JOIN reviews AS r ON r.seq = o.review_seq ")
                .append("ORDER BY seq DESC ")
                .append("LIMIT ? ")
                .append("OFFSET ? ")
                .toString();
        List<Order> result = jdbcTemplate.query(sql, mapper, pageable.getSize(), pageable.getOffset());
        return result;
    }

    @Override
    public Order findById(Long orderSeq) {
        String sql = new StringBuilder()
                .append("SELECT *, r.seq AS review_seq, r.create_at AS review_create_at ")
                .append("FROM orders AS o ")
                .append("LEFT OUTER JOIN reviews AS r ON r.seq = o.review_seq ")
                .append("WHERE o.seq = ? ")
                .toString();
        List<Order> orderList = jdbcTemplate.query(sql, mapper, orderSeq);
        return orderList.isEmpty() ? null : orderList.get(0);
    }

    @Override
    public int accept(Long userSeq, Long orderSeq) {
        String sql = "UPDATE orders SET state = ? WHERE seq = ? AND user_seq = ? AND state = ?";
        return jdbcTemplate.update(sql, Const.State.ACCEPTED.name(), orderSeq, userSeq, Const.State.REQUESTED.name());
    }

    @Override
    public int reject(Long userSeq, Long orderSeq, String rejectMsg) {
        String sql = "UPDATE orders SET state = ?, reject_msg = ?, rejected_At = now() WHERE seq = ? AND user_seq = ? AND state = ?";
        return jdbcTemplate.update(sql, Const.State.REJECTED.name(), rejectMsg, orderSeq, userSeq,
                Const.State.REQUESTED.name());
    }

    @Override
    public int shipping(Long userSeq, Long orderSeq) {
        String sql = "UPDATE orders SET state = ? WHERE seq = ? AND user_seq = ? AND state = ?";
        return jdbcTemplate.update(sql, Const.State.SHIPPING.name(), orderSeq, userSeq, Const.State.ACCEPTED.name());
    }

    @Override
    public int complete(Long userSeq, Long orderSeq) {
        String sql = "UPDATE orders SET state = ?, completed_At = now() WHERE seq = ? AND user_seq = ? AND state = ?";
        return jdbcTemplate.update(sql, Const.State.COMPLETED.name(), orderSeq, userSeq, Const.State.SHIPPING.name());
    }

    @Override
    public int insertReviewSeq(Long userSeq, Long orderSeq, Long reviewSeq) {
        String sql = "UPDATE orders SET review_seq = ? WHERE seq = ? AND user_seq = ?";
        return jdbcTemplate.update(sql, reviewSeq, orderSeq, userSeq);
    }

    static RowMapper<Order> mapper = (rs, rowNum) -> (new Order.Builder()
            .seq(rs.getLong("seq"))
            .userSeq(rs.getLong("user_seq"))
            .productSeq(rs.getLong("product_seq"))
            .review(rs.getLong("review_seq"), rs.getLong("user_seq"), rs.getLong("product_seq"),
                    rs.getString("content"), DateTimeUtils.dateTimeOf(rs.getTimestamp("review_create_at")))
            .state(rs.getString("state"))
            .requestMsg(rs.getString("request_msg"))
            .rejectMsg(rs.getString("reject_msg"))
            .completedAt(DateTimeUtils.dateTimeOf(rs.getTimestamp("completed_at")))
            .rejectedAt(DateTimeUtils.dateTimeOf(rs.getTimestamp("rejected_at")))
            .createAt(DateTimeUtils.dateTimeOf(rs.getTimestamp("create_at")))
            .build());

}
