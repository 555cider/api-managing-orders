package com.github.prgrms.orders;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private Long seq;

    private Long productId;

    private ReviewDto review;

    private String state;

    private String requestMessage;

    private String rejectMessage;

    private LocalDateTime completedAt;

    private LocalDateTime rejectedAt;

    private LocalDateTime createAt;

    public OrderDto(Order order) {
        if (order != null) {
            copyProperties(order, this);
            this.productId = order.getProduct().getSeq();
            this.review = order.getReview() == null ? null : new ReviewDto(order.getReview());
            this.requestMessage = order.getRequestMsg();
            this.rejectMessage = order.getRejectMsg();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("seq", this.seq)
                .append("productId", this.productId).append("review", this.review).append("state", this.state)
                .append("requestMessage", this.requestMessage).append("rejectMessage", this.rejectMessage)
                .append("completedAt", this.completedAt).append("rejectedAt", this.rejectedAt)
                .append("createAt", this.createAt)
                .toString();
    }

}
