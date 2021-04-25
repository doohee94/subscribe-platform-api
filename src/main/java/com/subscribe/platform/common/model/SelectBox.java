package com.subscribe.platform.common.model;

public class SelectBox<T> {

    private T value;
    private T label;

    public SelectBox(T value, T label) {
        this.value = value;
        this.label = label;
    }


    public T getValue() {
        return this.value;
    }


    public T getLabel() {
        return this.label;
    }
}
