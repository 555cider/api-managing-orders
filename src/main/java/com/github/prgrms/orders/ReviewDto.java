package com.github.prgrms.orders;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.BeanUtils;

public class ReviewDto {

    private Long seq;
    private Long productId;
    private String content;
    private LocalDateTime createAt;

    public ReviewDto(Review review) {
        BeanUtils.copyProperties(review, this);
        this.productId = review.getProductSeq();
    }

    public Long getSeq() {
        return this.seq;
    }

    public void setSeq(Long reviewSeq) {
        this.seq = reviewSeq;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productSeq) {
        this.productId = productSeq;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("productId", this.productId)
                .append("content", this.content).append("createAt", this.createAt).toString();
    }

}