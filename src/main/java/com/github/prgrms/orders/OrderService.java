package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional(readOnly = true)
	public List<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Optional<Order> findById(Long orderSeq) {
		return orderRepository.findById(orderSeq);
	}

	@Transactional
	public Boolean accept(Long userSeq, Long orderSeq) {
		if (orderRepository.accept(userSeq, orderSeq) < 1) {
			return false;
		}
		return true;
	}

	@Transactional
	public Boolean reject(Long userSeq, Long orderSeq, String rejectMsg) {
		if (orderRepository.reject(userSeq, orderSeq, rejectMsg) < 1) {
			return false;
		}
		return true;
	}

	@Transactional
	public Boolean shipping(Long userSeq, Long orderSeq) {
		if (orderRepository.shipping(userSeq, orderSeq) < 1) {
			return false;
		}
		return true;
	}

	@Transactional
	public Boolean complete(Long userSeq, Long orderSeq) {
		if (orderRepository.complete(userSeq, orderSeq) < 1) {
			return false;
		}
		return true;
	}

}
