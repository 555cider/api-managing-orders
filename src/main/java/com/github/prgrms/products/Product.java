package com.github.prgrms.products;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.time.LocalDateTime;
import java.util.Objects;

import com.google.common.base.Preconditions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Product {

	private final Long seq;
	private final String name;
	private String details;
	private final int reviewCount;
	private final LocalDateTime createAt;

	public Product(Long seq, String name, String details, int reviewCount, LocalDateTime createAt) {
		Preconditions.checkNotNull(name, "name must be provided");
		Preconditions.checkNotNull(reviewCount, "reviewCount must be provided");
		checkArgument((name.length() >= 1) && (name.length() <= 50),
				"name length must be between 1 and 50 characters");
		checkArgument(isEmpty(details) || (details.length() <= 1000),
				"details length must be less than 1000 characters");

		this.seq = seq;
		this.name = name;
		this.details = details;
		this.reviewCount = reviewCount;
		this.createAt = createAt;
	}

	public Long getSeq() {
		return this.seq;
	}

	public String getName() {
		return this.name;
	}

	public String getDetails() {
		return this.details;
	}

	public int getReviewCount() {
		return this.reviewCount;
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
		Product product = (Product) o;
		return Objects.equals(this.seq, product.seq);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.seq);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("seq", this.seq)
				.append("name", this.name).append("details", this.details)
				.append("reviewCount", this.reviewCount).append("createAt", this.createAt)
				.toString();
	}

	static public class Builder {
		private Long seq;
		private String name;
		private String details;
		private int reviewCount;
		private LocalDateTime createAt;

		public Builder() {}

		public Builder(Product product) {
			this.seq = product.seq;
			this.name = product.name;
			this.details = product.details;
			this.reviewCount = product.reviewCount;
			this.createAt = product.createAt;
		}

		public Builder seq(Long seq) {
			this.seq = seq;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder details(String details) {
			this.details = details;
			return this;
		}

		public Builder reviewCount(int reviewCount) {
			this.reviewCount = reviewCount;
			return this;
		}

		public Builder createAt(LocalDateTime createAt) {
			this.createAt = createAt;
			return this;
		}

		public Product build() {
			return new Product(this.seq, this.name, this.details, this.reviewCount, this.createAt);
		}
	}

}
