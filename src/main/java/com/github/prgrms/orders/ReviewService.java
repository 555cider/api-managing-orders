package com.github.prgrms.orders;

import javax.annotation.Resource;

import com.github.prgrms.errors.ReviewException;
import com.github.prgrms.products.ProductRepository;
import com.github.prgrms.utils.Const;
import com.google.common.base.Preconditions;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

	@Resource
	private OrderRepository orderRepository;

	@Resource
	private ReviewRepository reviewRepository;

	@Resource
	private ProductRepository productRepository;

	public Long review(Long userSeq, Long orderSeq, String content) {

		Preconditions.checkNotNull(userSeq, "userSeq must be provided");
		Preconditions.checkNotNull(content, "content must be provided");
		Preconditions.checkArgument(content.length() < 1001, "The length of content must be lower than 1001");

		Order order = orderRepository.findById(orderSeq);
		if (order == null
				|| order.getUserSeq() != userSeq
				|| order.getReview() != null
				|| !Const.State.COMPLETED.name().equals(order.getState())) {
			throw new ReviewException("");
		}

		Long reviewSeq = reviewRepository.review(userSeq, order.getProductSeq(), content);
		int result2 = orderRepository.insertReviewSeq(userSeq, orderSeq, reviewSeq);
		int result3 = productRepository.addReviewCount(order.getProductSeq());
		return reviewSeq;

	}

	@Transactional(readOnly = true)
	public Review findById(Long reviewSeq) {
		return reviewRepository.findById(reviewSeq);
	}

}
