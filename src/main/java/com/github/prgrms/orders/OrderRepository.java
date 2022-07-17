package com.github.prgrms.orders;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    @Modifying
    @Query(value = "UPDATE orders SET state = ACCEPTED WHERE seq = :orderSeq AND user_seq = :userSeq AND state = REQUESTED", nativeQuery = true)
    int accept(@Param("orderSeq") Long orderSeq, @Param("userSeq") Long userSeq);

    @Modifying
    @Query(value = "UPDATE orders SET state = REJECTED, reject_msg = :rejectMsg, rejected_At = now() WHERE seq = :orderSeq AND user_seq = :userSeq AND state = REQUESTED", nativeQuery = true)
    int reject(@Param("orderSeq") Long orderSeq, @Param("userSeq") Long userSeq, @Param("rejectMsg") String rejectMsg);

    @Modifying
    @Query(value = "UPDATE orders SET state = SHIPPING WHERE seq = :orderSeq AND user_seq = :userSeq AND state = ACCEPTED", nativeQuery = true)
    int shipping(@Param("orderSeq") Long orderSeq, @Param("userSeq") Long userSeq);

    @Modifying
    @Query(value = "UPDATE orders SET state = COMPLETE WHERE seq = :orderSeq AND user_seq = :userSeq AND state = SHIPPING", nativeQuery = true)
    int complete(@Param("orderSeq") Long orderSeq, @Param("userSeq") Long userSeq);

    @Modifying
    @Query(value = "UPDATE orders SET review_seq = :reviewSeq WHERE seq = :orderSeq AND user_seq = :userSeq", nativeQuery = true)
    int updateReviewSeq(@Param("orderSeq") Long orderSeq, @Param("userSeq") Long userSeq,
            @Param("reviewSeq") Long reviewSeq);

}
