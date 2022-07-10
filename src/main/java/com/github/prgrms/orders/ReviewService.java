package com.github.prgrms.orders;

import org.springframework.transaction.annotation.Transactional;

public interface ReviewService {

	public Long review(Long userSeq, Long orderSeq, String content);

	@Transactional(readOnly = true)
	public ReviewDto findBySeq(Long reviewSeq);

}
