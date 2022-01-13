package com.subscribe.platform.common.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
//@Profile("local")
@Getter
public class GlobalProperties {

    @Value("${C:\\Users\\BMJ\\project\\ReactProject\\subscribe-platform\\Subscribe-Platform-React\\public\\images}")
    private String fileUploadPath;
}
