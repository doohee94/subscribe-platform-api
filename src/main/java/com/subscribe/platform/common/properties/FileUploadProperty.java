package com.subscribe.platform.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file.upload")
@Getter @Setter
public class FileUploadProperty {
    private String location;
}
