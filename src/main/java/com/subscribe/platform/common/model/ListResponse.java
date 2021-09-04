package com.subscribe.platform.common.model;

import com.subscribe.platform.services.dto.ResServiceListDto;

import java.util.List;

public class ListResponse<T> extends ListResponseModel<T> {

    public ListResponse(List<T> content, long totCnt) {
        super(content, totCnt);
    }

    public ListResponse(List<T> list) {
        super(list);
    }
}
