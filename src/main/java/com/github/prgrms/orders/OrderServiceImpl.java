package com.github.prgrms.orders;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "OrderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public List<OrderDto> findAll(Pageable pageable) {
		Page<Order> orderPage = orderRepository.findAll(pageable);
		return orderPage.getContent().stream().map(order -> {
			OrderDto orderDto = new OrderDto(order);
			if (order.getReviewEntity().getSeq() != null && order.getReviewEntity().getSeq() != 0) {
				ReviewDto reviewDto = new ReviewDto(reviewRepository.findBySeq(order.getReviewEntity().getSeq()));
				orderDto.setReview(reviewDto);
			}
			return orderDto;
		}).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public OrderDto findBySeq(Long seq) {
		return new OrderDto(orderRepository.findBySeq(seq));
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
