package com.github.prgrms.products;

import static com.github.prgrms.utils.ApiUtils.success;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.prgrms.utils.ApiUtils.ApiResult;

@RestController
@RequestMapping("api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ApiResult<List<ProductDto>> findAll(Pageable pageable) {
        return success(productService.findAll(pageable));
    }

    @GetMapping(path = "{id}")
    public ApiResult<ProductDto> findBySeq(@PathVariable(name = "id") Long seq) {
        return success(productService.findBySeq(seq));
    }

}
