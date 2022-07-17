package com.github.prgrms.orders;

import javax.persistence.ManyToOne;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @ManyToOne
    @Query(value = "INSERT INTO reviews ( user_seq, product_seq, content ) VALUES ( :userSeq, :productSeq, :content )", nativeQuery = true)
    Long review(@Param("userSeq") Long userSeq, @Param("productSeq") Long productSeq, @Param("content") String content);

}