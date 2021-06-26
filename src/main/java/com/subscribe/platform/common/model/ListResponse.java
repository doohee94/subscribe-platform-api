package com.subscribe.platform.common.model;

import java.util.List;

public class ListResponse<T> extends ListResponseModel<T> {

    public ListResponse(List<T> content, long totCnt) {
        super(content, totCnt);
    }
}
