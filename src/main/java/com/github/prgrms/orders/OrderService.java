package com.github.prgrms.orders;

import java.util.List;

import com.github.prgrms.configures.web.Pageable;
import com.github.prgrms.errors.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Transactional(readOnly = true)
	public List<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Order findById(Long orderSeq) {
		Order order = orderRepository.findById(orderSeq);
		if (order == null) {
			throw new NotFoundException("");
		}
		return order;
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
