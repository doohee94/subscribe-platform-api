package com.subscribe.platform.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileInfo {

    private String originName;
    private String fakeName;

    public FileInfo(String originName, String fakeName) {
        this.originName = originName;
        this.fakeName = fakeName;
    }
}
