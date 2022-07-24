package com.github.prgrms.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Order SET state = 'ACCEPTED' WHERE seq = :orderSeq AND user_seq = :userSeq AND state = 'REQUESTED'")
    int accept(@Param("orderSeq") Long orderSeq, @Param("userSeq") Long userSeq);

    @Modifying
    @Query(value = "UPDATE Order SET state = 'REJECTED', reject_msg = :rejectMsg, rejected_At = now() WHERE seq = :orderSeq AND user_seq = :userSeq AND state = 'REQUESTED'", nativeQuery = true)
    int reject(@Param("orderSeq") Long orderSeq, @Param("userSeq") Long userSeq, @Param("rejectMsg") String rejectMsg);

    @Modifying
    @Query(value = "UPDATE Order SET state = 'SHIPPING' WHERE seq = :orderSeq AND user_seq = :userSeq AND state = 'ACCEPTED'")
    int shipping(@Param("orderSeq") Long orderSeq, @Param("userSeq") Long userSeq);

    @Modifying
    @Query(value = "UPDATE Order SET state = 'COMPLETE' WHERE seq = :orderSeq AND user_seq = :userSeq AND state = 'SHIPPING'")
    int complete(@Param("orderSeq") Long orderSeq, @Param("userSeq") Long userSeq);

}
