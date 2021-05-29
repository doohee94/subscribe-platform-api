package com.subscribe.platform.services.service;

import com.subscribe.platform.services.repository.ServicesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicesService {
    private final ServicesRepository servicesRepository;

    /**
     * 서비스 등록
     */
    public void createService(){

    }
}
