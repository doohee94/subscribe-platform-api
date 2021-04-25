package com.subscribe.platform.common.model;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class ListResponseModel<T> {

    private List<T> content;
    private long contentSize;

    public ListResponseModel(List<T> content) {
        this.content = content;
        this.contentSize = this.content.size();
    }
}
