package com.github.prgrms.orders;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

	@Transactional(readOnly = true)
	public List<OrderDto> findAll(Pageable pageable);

	@Transactional(readOnly = true)
	public OrderDto findBySeq(Long orderSeq);

	@Transactional
	public Boolean accept(Long orderSeq, Long userSeq);

	@Transactional
	public Boolean reject(Long orderSeq, Long userSeq, String rejectMsg);

	@Transactional
	public Boolean shipping(Long orderSeq, Long userSeq);

	@Transactional
	public Boolean complete(Long orderSeq, Long userSeq);

}
