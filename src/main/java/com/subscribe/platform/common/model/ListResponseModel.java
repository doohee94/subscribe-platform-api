package com.subscribe.platform.common.model;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class ListResponseModel<T> {

    private List<T> content;
    private long contentSize;
    private long totCnt;

    public ListResponseModel(List<T> content, long totCnt) {
        this.content = content;
        this.contentSize = this.content.size();
        this.totCnt = totCnt;
    }
}
