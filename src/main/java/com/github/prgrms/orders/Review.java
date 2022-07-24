package com.github.prgrms.orders;

import java.time.LocalDateTime;

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
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue
	private Long seq;

	@ManyToOne
	@JoinColumn(name = "user_seq", referencedColumnName = "seq")
	private User userEntity;

	@ManyToOne
	@JoinColumn(name = "product_seq", referencedColumnName = "seq")
	private Product productEntity;

	private String content;

	private LocalDateTime createAt;

	// public Review(Long seq, User userEntity, Product productEntity, String
	// content,
	// LocalDateTime createAt) {
	// checkNotNull(userSeq, "userSeq must be provided");
	// checkNotNull(productSeq, "productSeq must be provided");
	// checkNotNull(content, "content must be provided");
	// checkArgument(content.length() < 1000, "content length must be lower than
	// 1000");
	// this.seq = seq;
	// this.userEntity = userEntity;
	// this.productEntity = productEntity;
	// this.content = content;
	// this.createAt = createAt;
	// }

}
