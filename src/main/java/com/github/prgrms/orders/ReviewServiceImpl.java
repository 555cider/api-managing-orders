package com.github.prgrms.orders;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.products.Product;
import com.github.prgrms.products.ProductRepository;
import com.github.prgrms.users.User;
import com.github.prgrms.utils.Const;
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

	public ReviewDto review(Long userSeq, Long orderSeq, String content) throws NotFoundException {
		// [검증] 입력
		Preconditions.checkNotNull(userSeq, "userSeq must be provided");
		Preconditions.checkNotNull(content, "content must be provided");
		Preconditions.checkArgument(content.length() < 1001, "The length of content must be lower than 1001");

		// 1. 주문 조회
		Optional<Order> order = orderRepository.findById(orderSeq);

		// [검증] 주문 존재, 주문 상태, 리뷰 여부
		Preconditions.checkArgument(order.isPresent(), "No order was found");
		Preconditions.checkArgument(Const.State.COMPLETED.str.equals(order.get().getState()),
				"Review can be done only when state is completed");
		Preconditions.checkArgument(ObjectUtils.isEmpty(order.get().getReview()), "Review can be done only once");

		// 2. 리뷰 테이블에 리뷰 등록
		Long productSeq = order.get().getProduct().getSeq();
		Review review = new Review(new User(userSeq), new Product(productSeq), content);
		review = reviewRepository.save(review);

		// 3. 주문 테이블에 리뷰 번호 등록
		order.get().setReview(review);
		orderRepository.save(order.get());

		// 4. 제품 테이블에 리뷰 개수 증가
		order.get().getProduct().setReviewCount(order.get().getProduct().getReviewCount() + 1);
		productRepository.save(order.get().getProduct());

		return new ReviewDto(review);
	}

	public ReviewDto findById(Long reviewSeq) {
		Preconditions.checkNotNull(reviewSeq, "reviewSeq must be provided");

		Optional<Review> review = reviewRepository.findById(reviewSeq);
		Preconditions.checkArgument(review.isPresent(), "No review was found");

		return new ReviewDto(review.get());
	}

}
