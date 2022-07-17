package com.github.prgrms.products;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface ProductService {

    public ProductDto findById(Long seq);

    public List<ProductDto> findAll(Pageable pageable);

}
