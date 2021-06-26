package com.subscribe.platform.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class FileInfo {

    private String originName;
    private String fakeName;
    private String extensionName;   // 확장자명

    @Builder
    public FileInfo(String originName, String fakeName, String extensionName) {
        this.originName = originName;
        this.fakeName = fakeName;
        this.extensionName = extensionName;
    }
}
