package com.college.etms.util;

import com.college.etms.dto.common.PageResponse;
import org.springframework.data.domain.Page;

public final class PageResponseBuilder {

    private PageResponseBuilder() {
    }

    public static <T> PageResponse<T> build(Page<T> page, String sortBy, String sortDirection) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .build();
    }
}
