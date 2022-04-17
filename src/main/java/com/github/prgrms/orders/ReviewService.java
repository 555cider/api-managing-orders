package com.github.prgrms.orders;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.errors.ReviewException;
import com.github.prgrms.products.ProductRepository;
import com.github.prgrms.utils.Const;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;

	private final OrderRepository orderRepository;

	private final ProductRepository productRepository;

	public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository,
			ProductRepository productRepository) {
		this.reviewRepository = reviewRepository;
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	public Long review(Long userSeq, Long orderSeq, String content) {
		checkNotNull(userSeq, "userSeq must be provided");
		checkNotNull(content, "content must be provided");
		checkArgument(content.length() < 1001, "The length of content must be lower than 1001");

		Order order = orderRepository.findById(orderSeq).orElseThrow(() -> new NotFoundException(""));
		if (!Const.State.COMPLETED.name().equals(order.getState()) || order.getUserSeq() != userSeq
				|| order.getReviewSeq() != 0) {
			throw new ReviewException("");
		}

		Long reviewSeq = reviewRepository.review(userSeq, order.getProductSeq(), content);
		if (orderRepository.insertReviewSeq(userSeq, orderSeq, reviewSeq) < 1) {
			throw new ReviewException("");
		}
		if (productRepository.addReviewCount(order.getProductSeq()) < 1) {
			throw new ReviewException("");
		}
		return reviewSeq;
	}

	@Transactional(readOnly = true)
	public Optional<Review> findById(Long reviewSeq) {
		checkNotNull(reviewSeq, "reviewSeq must be provided");
		return reviewRepository.findById(reviewSeq);
	}

}
