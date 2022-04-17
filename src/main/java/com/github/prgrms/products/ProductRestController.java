package com.github.prgrms.products;

import static com.github.prgrms.utils.ApiUtils.success;
import static java.util.stream.Collectors.toList;

import java.util.List;

import com.github.prgrms.configures.web.Pageable;
import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.utils.ApiUtils.ApiResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ApiResult<List<ProductDto>> findAll(Pageable pageable) {
        return success(productService.findAll(pageable).stream().map(ProductDto::new).collect(toList()));
    }

    @GetMapping(path = "{id}")
    public ApiResult<ProductDto> findById(@PathVariable Long id) {
        return success(productService.findById(id).map(ProductDto::new)
                .orElseThrow(() -> new NotFoundException("Could not found product for " + id)));
    }
}
