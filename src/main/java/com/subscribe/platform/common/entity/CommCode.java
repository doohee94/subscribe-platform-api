package com.subscribe.platform.common.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comm_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommCode extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comm_code_id")
    private Long id;
    private String codeName;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code_id")
    private GroupCode groupCode;
}
