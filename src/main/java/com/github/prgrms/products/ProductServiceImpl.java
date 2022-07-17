package com.github.prgrms.products;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.prgrms.errors.NotFoundException;
import com.google.common.base.Preconditions;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.getContent().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    public ProductDto findById(Long seq) {
        Preconditions.checkNotNull(seq, "productId must be provided");

        Product product = productRepository.findById(seq)
                .orElseThrow(() -> new NotFoundException("No product was found"));

        return new ProductDto(product);
    }

}
