package com.subscribe.platform.common.handler;

import com.subscribe.platform.common.model.FileInfo;
import com.subscribe.platform.common.properties.GlobalProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileHandler {

    private final GlobalProperties globalProperties;

    public FileInfo getFileInfo(MultipartFile file) throws IOException{

        String contentType = file.getContentType();
        // 확장자명 없으면 에러
        if(ObjectUtils.isEmpty(contentType)){
            return null;
        }

        String originalFileExtension = "";
        // 파일 확장자명 설정
        if(contentType.contains("image/jpeg")){
            originalFileExtension = ".jpg";
        }
        else if(contentType.contains("image/png")){
            originalFileExtension = ".png";
        }
        else if(contentType.contains("image/gif")){
            originalFileExtension = ".gif";
        }

        // 저장할 이름 설정
        String fileFakeName = String.valueOf(UUID.randomUUID());

        saveFile(file, fileFakeName, originalFileExtension);

        // 파일정보 생성
        return FileInfo.builder()
                .originName(file.getOriginalFilename())
                .fakeName(fileFakeName)
                .extensionName(originalFileExtension)
                .build();
    }

    // 파일 저장
    public void saveFile(MultipartFile multipartFile, String fileFakeName, String originalFileExtension) throws IOException {

        // 저장할 위치의 디렉토리가 존지하지 않을 경우 생성
        File file = new File(globalProperties.getFileUploadPath()+fileFakeName+originalFileExtension);
        if(!file.exists()){
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }
        multipartFile.transferTo(file);

    }

}
