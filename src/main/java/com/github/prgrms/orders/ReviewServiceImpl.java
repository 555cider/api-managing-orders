package com.github.prgrms.orders;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.prgrms.errors.ReviewException;
import com.github.prgrms.products.ProductRepository;
import com.github.prgrms.utils.Const;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	public Long review(Long userSeq, Long orderSeq, String content) {
		checkNotNull(userSeq, "userSeq must be provided");
		checkNotNull(content, "content must be provided");
		checkArgument(content.length() < 1001, "The length of content must be lower than 1001");

		Order order = orderRepository.findBySeq(orderSeq);
		if (!Const.State.COMPLETED.name().equals(order.getState()) || order.getUserEntity().getSeq() != userSeq
				|| order.getReviewEntity().getSeq() != 0) {
			throw new ReviewException("");
		}

		Long reviewSeq = reviewRepository.review(userSeq, order.getProductEntity().getSeq(), content);
		if (orderRepository.updateReviewSeq(userSeq, orderSeq, reviewSeq) < 1) {
			throw new ReviewException("");
		}
		if (productRepository.addReviewCount(order.getProductEntity().getSeq()) < 1) {
			throw new ReviewException("");
		}
		return reviewSeq;
	}

	@Transactional(readOnly = true)
	public ReviewDto findBySeq(Long reviewSeq) {
		checkNotNull(reviewSeq, "reviewSeq must be provided");
		return new ReviewDto(reviewRepository.findBySeq(reviewSeq));
	}

}
