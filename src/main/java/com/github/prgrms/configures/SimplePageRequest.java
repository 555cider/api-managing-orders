package com.github.prgrms.configures;

import javax.annotation.Nullable;

import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.Assert;

public class SimplePageRequest extends AbstractPageRequest {

    private final Sort sort;

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @param sort must not be {@literal null}, use {@link Sort#unsorted()}
     *             instead.
     */
    protected SimplePageRequest(int page, int size, Sort sort) {

        super(page, size);

        Assert.notNull(sort, "Sort must not be null!");

        this.sort = sort;
    }

    /**
     * Creates a new unsorted {@link PageRequest}.
     *
     * @param page zero-based page index, must not be negative.
     * @param size the size of the page to be returned, must be greater than 0.
     * @since 2.0
     */
    public static SimplePageRequest of(int page, int size) {
        return of(page, size, Sort.unsorted());
    }

    /**
     * Creates a new {@link PageRequest} with sort parameters applied.
     *
     * @param page zero-based page index.
     * @param size the size of the page to be returned.
     * @param sort must not be {@literal null}, use {@link Sort#unsorted()}
     *             instead.
     * @since 2.0
     */
    public static SimplePageRequest of(int page, int size, Sort sort) {
        if (page < 0 || page > Long.MAX_VALUE) {
            page = 0;
        }
        if (size < 1 || size > 5) {
            size = 5;
        }
        return new SimplePageRequest(page, size, sort);
    }

    /**
     * Creates a new {@link PageRequest} with sort direction and properties applied.
     *
     * @param page       zero-based page index, must not be negative.
     * @param size       the size of the page to be returned, must be greater than
     *                   0.
     * @param direction  must not be {@literal null}.
     * @param properties must not be {@literal null}.
     * @since 2.0
     */
    public static SimplePageRequest of(int page, int size, Direction direction, String... properties) {
        return of(page, size, Sort.by(direction, properties));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Pageable#getSort()
     */
    public Sort getSort() {
        return sort;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Pageable#next()
     */
    @Override
    public Pageable next() {
        return new SimplePageRequest(getPageNumber() + 1, getPageSize(), getSort());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.AbstractPageRequest#previous()
     */
    @Override
    public SimplePageRequest previous() {
        return getPageNumber() == 0 ? this : new SimplePageRequest(getPageNumber() - 1, getPageSize(), getSort());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.data.domain.Pageable#first()
     */
    @Override
    public Pageable first() {
        return new SimplePageRequest(0, getPageSize(), getSort());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SimplePageRequest)) {
            return false;
        }

        SimplePageRequest that = (SimplePageRequest) obj;

        return super.equals(that) && this.sort.equals(that.sort);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return 31 * super.hashCode() + sort.hashCode();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(), sort);
    }

}
