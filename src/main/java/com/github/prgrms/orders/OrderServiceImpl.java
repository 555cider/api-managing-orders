package com.github.prgrms.orders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.prgrms.utils.Const;
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
		List<OrderDto> orderDtoList = orderPage.stream().map(
				(order) -> {
					OrderDto orderDto = new OrderDto(order);
					if (orderDto.getReview() != null) {
						Optional<Review> review = reviewRepository.findById(orderDto.getReview().getSeq());
						orderDto.setReview(new ReviewDto(review.get()));
					}
					return orderDto;
				}).collect(Collectors.toList());
		return orderDtoList;

	}

	public OrderDto findById(Long seq) {
		Preconditions.checkNotNull(seq, "orderId must be provided");

		Optional<Order> order = orderRepository.findById(seq);
		Preconditions.checkArgument(order.isPresent(), "No order was found");

		return new OrderDto(order.get());
	}

	@Transactional
	public Boolean accept(Long orderSeq, Long userSeq) {
		Optional<Order> orderOpt = orderRepository.findById(orderSeq);
		Preconditions.checkArgument(orderOpt.isPresent(), "No order was found");

		Order order = orderOpt.get();
		if (!userSeq.equals(order.getUser().getSeq())) {
			return false;
		}
		if (!Const.State.REQUESTED.str.equals(order.getState())) {
			return false;
		}

		order.setState("ACCEPTED");
		return true;
	}

	@Transactional
	public Boolean reject(Long orderSeq, Long userSeq, String rejectMsg) {
		Optional<Order> orderOpt = orderRepository.findById(orderSeq);
		Preconditions.checkArgument(orderOpt.isPresent(), "No order was found");

		Order order = orderOpt.get();
		if (!userSeq.equals(order.getUser().getSeq())) {
			return false;
		}
		if (!Const.State.REQUESTED.str.equals(order.getState())) {
			return false;
		}

		order.setState("REJECTED");
		order.setRejectMsg(rejectMsg);
		order.setRejectedAt(LocalDateTime.now());
		return true;
	}

	@Transactional
	public Boolean shipping(Long orderSeq, Long userSeq) {
		Optional<Order> orderOpt = orderRepository.findById(orderSeq);
		Preconditions.checkArgument(orderOpt.isPresent(), "No order was found");

		Order order = orderOpt.get();
		if (!userSeq.equals(order.getUser().getSeq())) {
			return false;
		}
		if (!Const.State.ACCEPTED.str.equals(order.getState())) {
			return false;
		}

		order.setState("SHIPPING");
		return true;
	}

	@Transactional
	public Boolean complete(Long orderSeq, Long userSeq) {
		Optional<Order> orderOpt = orderRepository.findById(orderSeq);
		Preconditions.checkArgument(orderOpt.isPresent(), "No order was found");

		Order order = orderOpt.get();
		if (!userSeq.equals(order.getUser().getSeq())) {
			return false;
		}
		if (!Const.State.SHIPPING.str.equals(order.getState())) {
			return false;
		}

		order.setState("COMPLETED");
		order.setCompletedAt(LocalDateTime.now());
		return true;
	}

}
