package com.github.prgrms.orders;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.errors.ReviewException;
import com.github.prgrms.products.ProductRepository;
import com.google.common.base.Preconditions;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;

	private final OrderRepository orderRepository;

	private final ProductRepository productRepository;

	public ReviewServiceImpl(ReviewRepository reviewRepository, OrderRepository orderRepository,
			ProductRepository productRepository) {
		this.reviewRepository = reviewRepository;
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	public Long review(Long userSeq, Long orderSeq, String content) throws NotFoundException {
		Preconditions.checkNotNull(userSeq, "userSeq must be provided");
		Preconditions.checkNotNull(content, "content must be provided");
		Preconditions.checkArgument(content.length() < 1001, "The length of content must be lower than 1001");

		Optional<Order> order = orderRepository.findById(orderSeq);
		Preconditions.checkArgument(order.isPresent(), "No order was found");

		Long reviewSeq = reviewRepository.review(userSeq, order.get().getProductEntity().getSeq(), content);
		if (orderRepository.updateReviewSeq(userSeq, orderSeq, reviewSeq) < 1) {
			throw new ReviewException("");
		}
		if (productRepository.addReviewCount(order.get().getProductEntity().getSeq()) < 1) {
			throw new ReviewException("");
		}
		return reviewSeq;
	}

	public ReviewDto findById(Long reviewSeq) {
		Preconditions.checkNotNull(reviewSeq, "reviewSeq must be provided");

		Optional<Review> review = reviewRepository.findById(reviewSeq);
		Preconditions.checkArgument(review.isPresent(), "No review was found");

		return new ReviewDto(review.get());
	}

}
