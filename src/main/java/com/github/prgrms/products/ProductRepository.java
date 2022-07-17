package com.github.prgrms.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Query(value = "UPDATE products SET review_count = review_count + 1 WHERE seq = :seq", nativeQuery = true)
    int addReviewCount(@Param("seq") Long seq);

}
