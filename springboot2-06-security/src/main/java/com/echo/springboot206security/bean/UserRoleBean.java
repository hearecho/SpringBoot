package com.echo.springboot206security.bean;

import com.echo.springboot206security.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRoleBean implements UserDetails {
    private UserEntity userEntity;
    private List<String> roles;

    public UserRoleBean(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.roles = userEntity.getRoles();
    }

    /**
     * 设置对象集合
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        for (String role:roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role));
        }
        return list;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
