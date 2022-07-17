package com.github.prgrms.orders;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	private final ReviewRepository reviewRepository;

	public OrderServiceImpl(OrderRepository orderRepository, ReviewRepository reviewRepository) {
		this.orderRepository = orderRepository;
		this.reviewRepository = reviewRepository;
	}

	public List<OrderDto> findAll(Pageable pageable) {
		Page<Order> orderPage = orderRepository.findAll(pageable);
		List<OrderDto> orderList = orderPage.getContent().stream().map(order -> {
			OrderDto orderDto = new OrderDto(order);
			Optional<Review> review = reviewRepository.findById(orderDto.getReview().getSeq());
			checkNotNull(review, "123123123123123");

			ReviewDto reviewDto = new ReviewDto(review.get());
			orderDto.setReview(reviewDto);
			return orderDto;
		}).collect(Collectors.toList());
		return orderList;
	}

	public OrderDto findById(Long seq) {
		checkNotNull(seq, "productId must be provided");

		Optional<Order> order = orderRepository.findById(seq);
		Preconditions.checkArgument(order.isPresent(), "No order was found");

		return new OrderDto(order.get());
	}

	@Transactional
	public Boolean accept(Long orderSeq, Long userSeq) {
		if (orderRepository.accept(userSeq, orderSeq) < 1) {
			return false;
		}
		return true;
	}

	@Transactional
	public Boolean reject(Long orderSeq, Long userSeq, String rejectMsg) {
		if (orderRepository.reject(userSeq, orderSeq, rejectMsg) < 1) {
			return false;
		}
		return true;
	}

	@Transactional
	public Boolean shipping(Long orderSeq, Long userSeq) {
		if (orderRepository.shipping(userSeq, orderSeq) < 1) {
			return false;
		}
		return true;
	}

	@Transactional
	public Boolean complete(Long orderSeq, Long userSeq) {
		if (orderRepository.complete(orderSeq, userSeq) < 1) {
			return false;
		}
		return true;
	}

}
