package com.github.prgrms.orders;

public interface ReviewService {

	public Long review(Long userSeq, Long orderSeq, String content);

	public ReviewDto findById(Long reviewSeq);

}
