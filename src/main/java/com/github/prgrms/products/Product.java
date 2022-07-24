package com.github.prgrms.products;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.base.Preconditions;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	private String name;

	private String details;

	private int reviewCount;

	private LocalDateTime createAt;

	public Product(String name, String details) {
		this(null, name, details, 0, null);
	}

	public Product(Long seq, String name, String details, int reviewCount, LocalDateTime createAt) {
		Preconditions.checkNotNull(name, "name must be provided");
		Preconditions.checkNotNull(reviewCount, "reviewCount must be provided");
		Preconditions.checkArgument((name.length() >= 1) && (name.length() <= 50),
				"name length must be between 1 and 50");
		Preconditions.checkArgument(ObjectUtils.isEmpty(details) || (details.length() <= 1000),
				"details length must be less than 1000");

		this.seq = seq;
		this.name = name;
		this.details = details;
		this.reviewCount = reviewCount;
		this.createAt = ObjectUtils.defaultIfNull(createAt, LocalDateTime.now());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Product product = (Product) o;
		return Objects.equals(this.seq, product.seq);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.seq);
	}

}
