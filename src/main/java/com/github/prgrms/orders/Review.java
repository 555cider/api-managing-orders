package com.github.prgrms.orders;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Review {

	private final Long seq;

	private Long userSeq;

	private Long productSeq;

	private String content;

	private final LocalDateTime createAt;

	public Review(Long seq, Long userSeq, Long productSeq, String content, LocalDateTime createAt) {
		checkNotNull(userSeq, "userSeq must be provided");
		checkNotNull(productSeq, "productSeq must be provided");
		checkNotNull(content, "content must be provided");
		checkArgument(content.length() < 1000, "content length must be lower than 1000");

		this.seq = seq;
		this.userSeq = userSeq;
		this.productSeq = productSeq;
		this.content = content;
		this.createAt = defaultIfNull(createAt, now());
	}

	public Long getSeq() {
		return seq;
	}

	public Long getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(Long userSeq) {
		this.userSeq = userSeq;
	}

	public Long getProductSeq() {
		return productSeq;
	}

	public void setProductSeq(Long productSeq) {
		this.productSeq = productSeq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Review review = (Review) o;
		return Objects.equals(seq, review.seq);
	}

	@Override
	public int hashCode() {
		return Objects.hash(seq);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("seq", seq).append("userSeq", userSeq)
				.append("productSeq", productSeq).append("content", content).append("createAt", createAt).toString();
	}

	static public class Builder {

		private Long seq;

		private Long userSeq;

		private Long productSeq;

		private String content;

		private LocalDateTime createAt;

		public Builder() {
		}

		public Builder(Review review) {
			this.seq = review.seq;
			this.userSeq = review.userSeq;
			this.productSeq = review.productSeq;
			this.content = review.content;
			this.createAt = review.createAt;
		}

		public Builder seq(Long seq) {
			this.seq = seq;
			return this;
		}

		public Builder userSeq(Long userSeq) {
			this.userSeq = userSeq;
			return this;
		}

		public Builder productSeq(Long productSeq) {
			this.productSeq = productSeq;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public Builder createAt(LocalDateTime createAt) {
			this.createAt = createAt;
			return this;
		}

		public Review build() {
			return new Review(seq, userSeq, productSeq, content, createAt);
		}
	}

}
