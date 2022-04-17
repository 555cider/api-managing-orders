package com.github.prgrms.products;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Optional;

import com.github.prgrms.configures.web.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(Long productId) {
        checkNotNull(productId, "productId must be provided");
        return productRepository.findById(productId);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
