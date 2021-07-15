package com.subscribe.platform.services.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ResCategoryDto {

    private Long categoryId;
    private String categoryName;

    @Builder
    public ResCategoryDto(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
