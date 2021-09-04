package com.subscribe.platform.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor
public class PageableDto {

    private Integer size;
    private Integer page;
    private Sort.Direction sort;

    public PageableDto(Integer size, Integer page, Sort.Direction sort) {

        this.size = size == null ? 10 : size;
        this.page = page == null ? 0 : page;
        this.sort = sort;
    }

    public PageRequest toPageRequest() {
        if (sort == null) {
            return PageRequest.of(this.page, this.size);
        }
        return PageRequest.of(this.page, this.size, this.sort);
    }


}
