package com.github.prgrms.products;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

public interface ProductRepository {

    List<Product> findAll(Pageable pageable);

    Optional<Product> findById(Long id);

    int addReviewCount(Long productSeq);

}
