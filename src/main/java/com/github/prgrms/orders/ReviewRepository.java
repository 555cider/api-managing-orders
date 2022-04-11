package com.github.prgrms.orders;

public interface ReviewRepository {

    Review findById(Long reviewSeq);

    Long review(Long userSeq, Long productSeq, String content);

}