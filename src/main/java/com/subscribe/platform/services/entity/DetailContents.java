package com.subscribe.platform.services.entity;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class DetailContents {

    private String contents;
    private String delivery;
    private String etc;

    // 값타입은 변경 불가능하게 설계
    // 임베디드타입은 기본생성자를 public 또는 protected로 설정 : JPA 구현 라이브러리가 객체를 생성할 때 리플랙션 같은 기술을 사용할 수 있도록 지원해야함
    protected DetailContents(){}
    public DetailContents(String contents, String delivery, String etc){
        this.contents = contents;
        this.delivery = delivery;
        this.etc = etc;
    }
}
