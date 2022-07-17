package com.github.prgrms.orders;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

	public List<OrderDto> findAll(Pageable pageable);

	public OrderDto findById(Long seq);

	public Boolean accept(Long seq, Long userSeq);

	@Transactional
	public Boolean reject(Long seq, Long userSeq, String rejectMsg);

	@Transactional
	public Boolean shipping(Long seq, Long userSeq);

	@Transactional
	public Boolean complete(Long seq, Long userSeq);

}
