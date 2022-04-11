package com.github.prgrms.orders;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Order {

	private final Long seq;
	private final Long userSeq;
	private final Long productSeq;
	private Review review;
	private String state;
	private String requestMsg;
	private String rejectMsg;
	private LocalDateTime completedAt;
	private LocalDateTime rejectedAt;
	private final LocalDateTime createAt;

	public Order(Long seq, Long userSeq, Long productSeq, Review review, String state,
			String requestMsg, String rejectMsg, LocalDateTime completedAt,
			LocalDateTime rejectedAt, LocalDateTime createAt) {
		checkArgument(isEmpty(requestMsg) || (requestMsg.length() <= 1000),
				"requestMsg length must be less than 1000 characters");
		checkArgument(isEmpty(rejectMsg) || (rejectMsg.length() <= 1000),
				"rejectMsg length must be less than 1000 characters");

		this.seq = seq;
		this.userSeq = userSeq;
		this.productSeq = productSeq;
		this.review = review;
		this.state = state;
		this.requestMsg = requestMsg;
		this.rejectMsg = rejectMsg;
		this.completedAt = completedAt;
		this.rejectedAt = rejectedAt;
		this.createAt = createAt;
	}

	public Long getSeq() {
		return this.seq;
	}

	public Long getUserSeq() {
		return this.userSeq;
	}

	public Long getProductSeq() {
		return this.productSeq;
	}

	public Review getReview() {
		return this.review;
	}

	public String getState() {
		return this.state;
	}

	public String getRequestMsg() {
		return this.requestMsg;
	}

	public String getRejectMsg() {
		return this.rejectMsg;
	}

	public LocalDateTime getCompletedAt() {
		return this.completedAt;
	}

	public LocalDateTime getRejectedAt() {
		return this.rejectedAt;
	}

	public LocalDateTime getCreateAt() {
		return this.createAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Order order = (Order) o;
		return Objects.equals(this.seq, order.seq);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.seq);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("seq", this.seq)
				.append("userSeq", this.userSeq)
				.append("productSeq", this.productSeq)
				.append("review", this.review)
				.append("state", this.state)
				.append("requestMsg", this.requestMsg)
				.append("rejectMsg", this.rejectMsg)
				.append("completedAt", this.completedAt)
				.append("rejectedAt", this.rejectedAt)
				.append("createAt", this.createAt)
				.toString();
	}

	static public class Builder {
		private Long seq;
		private Long userSeq;
		private Long productSeq;
		private Review review;
		private String state;
		private String requestMsg;
		private String rejectMsg;
		private LocalDateTime completedAt;
		private LocalDateTime rejectedAt;
		private LocalDateTime createAt;

		public Builder() {
		}

		public Builder(Order order) {
			this.seq = order.seq;
			this.userSeq = order.userSeq;
			this.productSeq = order.productSeq;
			this.review = order.review;
			this.state = order.state;
			this.requestMsg = order.requestMsg;
			this.rejectMsg = order.rejectMsg;
			this.completedAt = order.completedAt;
			this.rejectedAt = order.rejectedAt;
			this.createAt = order.createAt;
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

		public Builder review(Long reviewSeq, Long userSeq, Long productSeq, String content, LocalDateTime createAt) {
			try {
				this.review = new Review(reviewSeq, userSeq, productSeq, content, createAt);
			} catch (Exception e) {
				this.review = null;
			}
			return this;
		}

		public Builder state(String string) {
			this.state = string;
			return this;
		}

		public Builder requestMsg(String string) {
			this.requestMsg = string;
			return this;
		}

		public Builder rejectMsg(String string) {
			this.rejectMsg = string;
			return this;
		}

		public Builder completedAt(LocalDateTime completedAt) {
			this.completedAt = completedAt;
			return this;
		}

		public Builder rejectedAt(LocalDateTime rejecetedAt) {
			this.rejectedAt = rejecetedAt;
			return this;
		}

		public Builder createAt(LocalDateTime createAt) {
			this.createAt = createAt;
			return this;
		}

		public Order build() {
			return new Order(this.seq, this.userSeq, this.productSeq, this.review, this.state,
					this.requestMsg, this.rejectMsg, this.completedAt, this.rejectedAt,
					this.createAt);
		}
	}

}
