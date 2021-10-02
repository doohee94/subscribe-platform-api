package com.subscribe.platform.common.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_code")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_code_id")
    private Long id;
    private String groupName;
    private String description;

    @OneToMany(mappedBy = "groupCode", cascade = CascadeType.ALL)
    private Set<CommCode> commCodes = new HashSet<>();
}
