package com.subscribe.platform.user.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Embedded
    private Email email;
    @Embedded
    private Password password;
    @Embedded
    private Address address;

    //TODO: many to many 로 바꿔야 할듯
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    public boolean isMatchPassword(String password) {
        return false;
    }
}
