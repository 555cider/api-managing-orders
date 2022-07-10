package com.github.prgrms.products;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ProductService {

    @Transactional(readOnly = true)
    public ProductDto findBySeq(Long seq);

    @Transactional(readOnly = true)
    public List<ProductDto> findAll(Pageable pageable);

}
