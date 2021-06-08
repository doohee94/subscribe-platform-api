package com.subscribe.platform.services.controller;

import com.subscribe.platform.services.dto.CreateServiceDto;
import com.subscribe.platform.services.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
public class ServicesController {
    private final ServicesService servicesService;

    @PostMapping("/addService")
    public void createService(CreateServiceDto createServiceDto){
        servicesService.createService(createServiceDto);
    }
}
