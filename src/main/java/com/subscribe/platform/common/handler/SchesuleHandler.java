package com.subscribe.platform.common.handler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class SchesuleHandler {

//    @Scheduled(fixedDelay = 10000)   // scheduler 끝나는 시간 기준으로 10초 간격으로 실행
//    public void schedulerTestTask1(){
//        System.out.println("fixedDelay test = "+ LocalDateTime.now());
//        System.out.println("Current Thread : "+ Thread.currentThread().getName());
//    }
//
//    @Scheduled(fixedRate = 20000)    // scheduler 시작하는 시간 기준으로 20초 간격으로 실행
//    public void schedulerTestTask2(){
//        System.out.println("fixedRate test = "+ LocalDateTime.now());
//        System.out.println("Current Thread : "+ Thread.currentThread().getName());
//    }
//
//    @Scheduled(cron = "0 30 20 * * ?", zone = "Asia/Seoul")   // 서울시각 기준으로 매일 20시 30분에 실행
//    public void schedulerTestTask3(){
//        System.out.println("cron test = "+ LocalDateTime.now());
//    }
}
