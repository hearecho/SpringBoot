package com.echo.springboot206security.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Proxy(lazy = false)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "usersec_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> rolelist;

    public UserEntity() {
    }

    public UserEntity(String username, String password, Set<RoleEntity> rolelist) {
        this.username = username;
        this.password = password;
        this.rolelist = rolelist;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' + +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRolelist() {
        return rolelist;
    }

    public void setRolelist(Set<RoleEntity> rolelist) {
        this.rolelist = rolelist;
    }

    /**
     * 获取role list
     * @return
     */
    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        for (RoleEntity roleEntity:rolelist) {
            roles.add(roleEntity.getRole());
        }

        return roles;
    }
}
