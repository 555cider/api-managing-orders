package com.github.prgrms.orders;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

public interface OrderRepository {

    List<Order> findAll(Pageable pageable);

    Optional<Order> findById(Long orderSeq);

    int accept(Long userSeq, Long orderSeq);

    int reject(Long userSeq, Long orderSeq, String rejectMsg);

    int shipping(Long userSeq, Long orderSeq);

    int complete(Long userSeq, Long orderSeq);

    int insertReviewSeq(Long userSeq, Long orderSeq, Long reviewSeq);

}
