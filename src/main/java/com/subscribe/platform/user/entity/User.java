package com.subscribe.platform.user.entity;

import com.subscribe.platform.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Collections;
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

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(nullable = false)
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Store store;


    /**
     * 일반 사용자의 경우 store 정보를 입력받지 않고 판매자의 경우 추가로 store의 정보를 입력받는다.
     * 이때 빌더의 이름을 부여해서 책임을 명확하게 하고 만약 둘의 받는 인자가 다르다면 받아야하는 인자도 명확하게 해준다.
     */
    // 일반 사용자 생성자
    @Builder(builderClassName = "createMemberBuilder", builderMethodName = "createMemberBuilder")
    public User(String name, String email, String password, Authority authorities) {

        this.name = name;
        this.email = email;
        this.password = new Password(password);
        this.authorities = new HashSet<>();
        this.authorities.add(authorities);
        this.userStatus = UserStatus.WAITING;
    }

    // 판매자 생성자
    @Builder(builderClassName = "createStoreBuilder", builderMethodName = "createStoreBuilder")
    public User(String name, String email, String password, Authority authorities, Store store) {

        this.name = name;
        this.email = email;
        this.password = new Password(password);
        this.authorities = new HashSet<>();
        this.authorities.add(authorities);
        this.userStatus = UserStatus.WAITING;
        this.store = store;
    }

}
