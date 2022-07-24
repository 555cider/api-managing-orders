package com.github.prgrms.orders;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private Long seq;

    private Long productId;

    private String content;

    private LocalDateTime createAt;

    public ReviewDto(Review review) {
        if (review != null) {
            copyProperties(review, this);
            this.productId = review.getProduct().getSeq();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("productId", this.productId)
                .append("content", this.content)
                .append("createAt", this.createAt)
                .toString();
    }

}
