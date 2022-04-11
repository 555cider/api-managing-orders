package com.github.prgrms.orders;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

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
        BeanUtils.copyProperties(order, this);
        this.productId = order.getProductSeq();
        if (order.getReview() != null) {
            this.review = new ReviewDto(order.getReview());
        }
        this.requestMessage = order.getRequestMsg();
        this.rejectMessage = order.getRejectMsg();
    }

    public Long getSeq() {
        return this.seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ReviewDto getReview() {
        return this.review;
    }

    public void setReviewDto(ReviewDto reviewDto) {
        this.review = reviewDto;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRequestMessage() {
        return this.requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getRejectMessage() {
        return this.rejectMessage;
    }

    public void setRejectMessage(String rejectMessage) {
        this.rejectMessage = rejectMessage;
    }

    public LocalDateTime getCompletedAt() {
        return this.completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getRejectedAt() {
        return this.rejectedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", this.seq)
                .append("productId", this.productId)
                .append("review", this.review)
                .append("state", this.state)
                .append("requestMessage", this.requestMessage)
                .append("rejectMessage", this.rejectMessage)
                .append("completedAt", this.completedAt)
                .append("rejectedAt", this.rejectedAt)
                .append("createAt", this.createAt)
                .toString();
    }

}
