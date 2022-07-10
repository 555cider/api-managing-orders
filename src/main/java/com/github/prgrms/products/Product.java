package com.github.prgrms.products;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Preconditions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue
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
		checkArgument((name.length() >= 1) && (name.length() <= 50), "name length must be between 1 and 50");
		checkArgument(isEmpty(details) || (details.length() <= 1000), "details length must be less than 1000");

		this.seq = seq;
		this.name = name;
		this.details = details;
		this.reviewCount = reviewCount;
		this.createAt = defaultIfNull(createAt, now());
	}

	public void setName(String name) {
		checkArgument(isNotEmpty(name), "name must be provided");
		checkArgument(name.length() >= 1 && name.length() <= 50, "name length must be between 1 and 50 characters");
		this.name = name;
	}

	public void setDetails(String details) {
		checkArgument(isEmpty(details) || details.length() <= 1000, "details length must be less than 1000 characters");
		this.details = details;
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

	public ProductDto toProductDto() {
		ProductDto productDto = new ProductDto();
		productDto.setSeq(seq);
		productDto.setName(name);
		productDto.setDetails(details);
		productDto.setReviewCount(reviewCount);
		productDto.setCreateAt(createAt);
		return productDto;
	}

}
