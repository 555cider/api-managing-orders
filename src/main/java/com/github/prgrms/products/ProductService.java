package com.github.prgrms.products;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.github.prgrms.configures.web.Pageable;
import com.github.prgrms.errors.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).stream().map(ProductDto::new).collect(toList());
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long productSeq) {
        Product product = productRepository.findById(productSeq);
        if (product == null) {
            throw new NotFoundException("");
        }
        return new ProductDto(product);
    }

}
