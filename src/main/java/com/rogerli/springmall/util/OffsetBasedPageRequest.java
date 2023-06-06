package com.rogerli.springmall.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetBasedPageRequest implements Pageable {

    private final int limit;

    private final int offset;
    private final int pageNumber;

    // Constructor could be expanded if sorting is needed
    private final Sort sort ;

    public OffsetBasedPageRequest(int limit, int offset, int pageNumber, Sort sort) {
        this.pageNumber = pageNumber;
        this.sort = sort;
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        // Typecast possible because number of entries cannot be bigger than integer (primary key is integer)
        return new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() + getPageSize()), pageNumber, sort);
    }

    public Pageable previous() {
        // The integers are positive. Subtracting does not let them become bigger than integer.
        return hasPrevious() ?
                new OffsetBasedPageRequest(getPageSize(), (int) (getOffset() - getPageSize()), pageNumber, sort): this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(getPageSize(), 0, pageNumber, sort);
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
