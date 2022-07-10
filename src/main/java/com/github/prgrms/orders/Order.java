package com.github.prgrms.orders;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.prgrms.products.Product;
import com.github.prgrms.users.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue
	private Long seq;

	@ManyToOne
	@JoinColumn(name = "user_seq", referencedColumnName = "seq")
	private User userEntity;

	@ManyToOne
	@JoinColumn(name = "product_seq", referencedColumnName = "seq")
	private Product productEntity;

	@ManyToOne
	@JoinColumn(name = "review_seq", referencedColumnName = "seq")
	private Review reviewEntity;

	private String state;

	private String requestMsg;

	private String rejectMsg;

	private LocalDateTime completedAt;

	private LocalDateTime rejectedAt;

	private LocalDateTime createAt;

	public Order(Long seq, User userEntity, Product productEntity, Review reviewEntity,
			String state, String requestMsg,
			String rejectMsg, LocalDateTime completedAt, LocalDateTime rejectedAt, LocalDateTime createAt) {
		this.seq = seq;
		this.userEntity = userEntity;
		this.productEntity = productEntity;
		this.reviewEntity = reviewEntity;
		this.state = state;
		this.requestMsg = requestMsg;
		this.rejectMsg = rejectMsg;
		this.completedAt = completedAt;
		this.rejectedAt = rejectedAt;
		this.createAt = createAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Order order = (Order) o;
		return Objects.equals(seq, order.seq);
	}

	@Override
	public int hashCode() {
		return Objects.hash(seq);
	}

}
