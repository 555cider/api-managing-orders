package com.github.prgrms.orders;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
			orderDto.setReview(new ReviewDto(review.get()));
			return orderDto;
		}).collect(Collectors.toList());
		return orderList;
	}

	public OrderDto findById(Long seq) {
		checkNotNull(seq, "orderId must be provided");

		Optional<Order> order = orderRepository.findById(seq);
		Preconditions.checkArgument(order.isPresent(), "No order was found");

		return new OrderDto(order.get());
	}

	public Boolean accept(Long orderSeq, Long userSeq) {
		orderRepository.accept(orderSeq, userSeq);
		return true;
	}

	public Boolean reject(Long orderSeq, Long userSeq, String rejectMsg) {
		orderRepository.reject(userSeq, orderSeq, rejectMsg);
		return true;
	}

	public Boolean shipping(Long orderSeq, Long userSeq) {
		orderRepository.shipping(userSeq, orderSeq);
		return true;
	}

	public Boolean complete(Long orderSeq, Long userSeq) {
		orderRepository.complete(orderSeq, userSeq);
		return true;
	}

}
