package com.subscribe.platform.user.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
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

    private String email;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;


    @Builder
    public User(String name, String email, String password, Authority authorities) {
        this.name = name;
        this.email = email;
        this.password = new Password(password);

        this.authorities = new HashSet<>();

        this.authorities.add(authorities);
        this.userStatus = UserStatus.WAITING;
    }



}
