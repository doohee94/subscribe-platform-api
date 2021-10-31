package com.subscribe.platform.services.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ReqModifyServiceDto {

    @NotNull
    private Long serviceId;
    @NotBlank
    private String serviceName;
    @NotBlank
    private String serviceCycle;
    @NotBlank
    private String availableDay;
    @NotBlank
    private String detailContents;

    @NotEmpty
    private List<ReqModifyServiceOptionDto> serviceOptions;
    @NotEmpty
    private List<ReqModifyServiceImageDto> serviceImages;
    @NotEmpty
    private List<ReqModifyServiceCategoryDto> categories;
}
