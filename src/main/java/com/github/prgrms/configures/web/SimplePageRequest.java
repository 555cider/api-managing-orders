package com.github.prgrms.configures.web;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SimplePageRequest implements Pageable {

    private final Integer offset;
    private final Integer size;

    public SimplePageRequest() {
        this(0, 5);
    }

    public SimplePageRequest(Integer offset, Integer size) {
        if ((offset == null) || (offset < 0) || (offset > Long.MAX_VALUE)) {
            this.offset = 0;
        } else {
            this.offset = offset;
        }

        if ((size == null) || (size < 1) || (size > 5)) {
            this.size = 5;
        } else {
            this.size = size;
        }
    }

    @Override
    public Integer getOffset() {
        return this.offset;
    }

    @Override
    public Integer getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("offset", this.offset)
                .append("size", this.size).toString();
    }

}