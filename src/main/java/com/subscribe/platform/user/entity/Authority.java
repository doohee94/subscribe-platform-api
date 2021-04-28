package com.subscribe.platform.user.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table
public class Authority extends BaseTimeEntity implements GrantedAuthority {

    @Id
    private long id;

    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private Set<User> user;

    @Override
    public String getAuthority() {
        return authority;
    }
}
