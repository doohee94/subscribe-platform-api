package com.subscribe.platform.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTestController {

    @GetMapping(value = "/api/test/apiTest.do")
    public String apiTest(@Param("id") String id, @Param("name") String name){

        if("maro42".equals(id) && "방민주".equals(name)){
            return "Y";
        }
        return "N";
    }
}
