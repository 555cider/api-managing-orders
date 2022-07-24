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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue
	private Long seq;

	@ManyToOne
	@JoinColumn(name = "user_seq", referencedColumnName = "seq")
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_seq", referencedColumnName = "seq")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "review_seq", referencedColumnName = "seq")
	private Review review;

	private String state;

	private String requestMsg;

	private String rejectMsg;

	private LocalDateTime completedAt;

	private LocalDateTime rejectedAt;

	private LocalDateTime createAt;

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
