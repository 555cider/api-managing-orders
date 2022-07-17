package com.github.prgrms.products;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long seq;

    private String name;

    private String details;

    private int reviewCount;

    private LocalDateTime createAt;

    public ProductDto() {
    }

    public ProductDto(Product source) {
        copyProperties(source, this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("seq", seq).append("name", name)
                .append("details", details).append("reviewCount", reviewCount).append("createAt", createAt).toString();
    }

}
