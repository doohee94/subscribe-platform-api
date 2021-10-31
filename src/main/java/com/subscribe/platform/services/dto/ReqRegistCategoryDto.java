package com.subscribe.platform.services.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ReqRegistCategoryDto {
    @NotBlank
    private Long categoryId;
}
