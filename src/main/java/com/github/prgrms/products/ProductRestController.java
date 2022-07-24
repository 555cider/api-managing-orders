package com.github.prgrms.products;

import static com.github.prgrms.utils.ApiUtils.success;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.prgrms.utils.ApiUtils.ApiResult;

@RestController
@RequestMapping("api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ApiResult<List<ProductDto>> findAll(Pageable pageable) {
        return success(productService.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(
                Order.desc("seq")))));
    }

    @GetMapping(path = "{id}")
    public ApiResult<ProductDto> findById(@PathVariable(name = "id") Long seq) {
        return success(productService.findById(seq));
    }

}
