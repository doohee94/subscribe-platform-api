package com.subscribe.platform.user.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table
public class Authority implements GrantedAuthority {

    @Id
    private long id;
    private String authority;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String getAuthority() {
        return authority;
    }
}
