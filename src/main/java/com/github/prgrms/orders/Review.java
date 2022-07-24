package com.github.prgrms.orders;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.prgrms.products.Product;
import com.github.prgrms.users.User;
import com.google.common.base.Preconditions;

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
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@ManyToOne
	@JoinColumn(name = "user_seq", referencedColumnName = "seq")
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_seq", referencedColumnName = "seq")
	private Product product;

	private String content;

	private LocalDateTime createAt;

	public Review(User user, Product product, String content) {
		Preconditions.checkNotNull(user.getSeq(), "userSeq must be provided");
		Preconditions.checkNotNull(product.getSeq(), "productSeq must be provided");
		Preconditions.checkNotNull(content, "content must be provided");
		Preconditions.checkArgument(content.length() < 1000, "content length must be lower than 1000");
		this.user = user;
		this.product = product;
		this.content = content;
	}

}
