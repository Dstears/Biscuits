package com.mbiscuit.core.common.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class PageParam {

    private int page;

    private int size;

    public PageParam() {
        this.page = 1;
        this.size = 10;
    }

    public static PageParam getInstance(int page, int size) {
        PageParam pageParam = new PageParam();
        pageParam.size = size;
        pageParam.page = page;
        return pageParam;
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}
