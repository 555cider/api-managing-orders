package com.github.prgrms.products;

import java.util.List;

import com.github.prgrms.configures.web.Pageable;

public interface ProductRepository {

    List<Product> findAll(Pageable pageable);

    Product findById(Long id);

    int addReviewCount(Long productSeq);

}
