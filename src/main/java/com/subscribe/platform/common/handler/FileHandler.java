package com.subscribe.platform.common.handler;

import com.subscribe.platform.common.model.FileInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class FileHandler {

    public FileInfo parseFileInfo(MultipartFile multipartFile){

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if(multipartFile.isEmpty()){
            return new FileInfo();
        }

        // 파일이름 = 업로드한 날짜지만 나중에 랜덤 숫자 가져와서 뿌리자
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String current_date = simpleDateFormat.format(new Date());


    }

}
