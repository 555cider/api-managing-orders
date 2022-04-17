package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;
import com.github.prgrms.utils.Const;
import com.github.prgrms.utils.DateTimeUtils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOrderRepository implements OrderRepository {

        private final JdbcTemplate jdbcTemplate;

        public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public List<Order> findAll(Pageable pageable) {
                String sql = new StringBuilder()
                                .append("SELECT * ")
                                .append("FROM orders ")
                                .append("ORDER BY seq DESC LIMIT ? OFFSET ? ")
                                .toString();
                List<Order> result = jdbcTemplate.query(sql, mapper, pageable.getSize(), pageable.getOffset());
                return result;
        }

        @Override
        public Optional<Order> findById(Long orderSeq) {
                String sql = new StringBuilder()
                                .append("SELECT * ")
                                .append("FROM orders ")
                                .append("WHERE seq = ? ")
                                .toString();
                List<Order> orderList = jdbcTemplate.query(sql, mapper, orderSeq);
                return Optional.ofNullable(orderList.isEmpty() ? null : orderList.get(0));
        }

        @Override
        public int accept(Long userSeq, Long orderSeq) {
                String sql = new StringBuilder()
                                .append("UPDATE orders ")
                                .append("SET state = ? ")
                                .append("WHERE seq = ? AND user_seq = ? AND state = ? ")
                                .toString();
                return jdbcTemplate.update(sql, Const.State.ACCEPTED.name(), orderSeq, userSeq,
                                Const.State.REQUESTED.name());
        }

        @Override
        public int reject(Long userSeq, Long orderSeq, String rejectMsg) {
                String sql = new StringBuilder()
                                .append("UPDATE orders ")
                                .append("SET state = ?, reject_msg = ?, rejected_At = now() ")
                                .append("WHERE seq = ? AND user_seq = ? AND state = ? ")
                                .toString();
                return jdbcTemplate.update(sql, Const.State.REJECTED.name(), rejectMsg, orderSeq, userSeq,
                                Const.State.REQUESTED.name());
        }

        @Override
        public int shipping(Long userSeq, Long orderSeq) {
                String sql = new StringBuilder()
                                .append("UPDATE orders ")
                                .append("SET state = ? ")
                                .append("WHERE seq = ? AND user_seq = ? AND state = ? ")
                                .toString();
                return jdbcTemplate.update(sql, Const.State.SHIPPING.name(), orderSeq, userSeq,
                                Const.State.ACCEPTED.name());
        }

        @Override
        public int complete(Long userSeq, Long orderSeq) {
                String sql = new StringBuilder()
                                .append("UPDATE orders ")
                                .append("SET state = ?, completed_At = now() ")
                                .append("WHERE seq = ? AND user_seq = ? AND state = ? ")
                                .toString();
                return jdbcTemplate.update(sql, Const.State.COMPLETED.name(), orderSeq, userSeq,
                                Const.State.SHIPPING.name());
        }

        @Override
        public int insertReviewSeq(Long userSeq, Long orderSeq, Long reviewSeq) {
                String sql = new StringBuilder()
                                .append("UPDATE orders ")
                                .append("SET review_seq = ? ")
                                .append("WHERE seq = ? AND user_seq = ? ")
                                .toString();
                return jdbcTemplate.update(sql, reviewSeq, orderSeq, userSeq);
        }

        static RowMapper<Order> mapper = (rs, rowNum) -> (new Order.Builder()
                        .seq(rs.getLong("seq"))
                        .userSeq(rs.getLong("user_seq"))
                        .productSeq(rs.getLong("product_seq"))
                        .reviewSeq(rs.getLong("review_seq"))
                        .state(rs.getString("state"))
                        .requestMsg(rs.getString("request_msg"))
                        .rejectMsg(rs.getString("reject_msg"))
                        .completedAt(DateTimeUtils.dateTimeOf(rs.getTimestamp("completed_at")))
                        .rejectedAt(DateTimeUtils.dateTimeOf(rs.getTimestamp("rejected_at")))
                        .createAt(DateTimeUtils.dateTimeOf(rs.getTimestamp("create_at")))
                        .build());

}
