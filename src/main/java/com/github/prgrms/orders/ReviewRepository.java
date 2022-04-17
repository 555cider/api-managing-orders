package com.github.prgrms.orders;

import java.util.Optional;

public interface ReviewRepository {

    Optional<Review> findById(Long reviewSeq);

    Long review(Long userSeq, Long productSeq, String content);

}