package com.echo.springboot206security.service;

import com.echo.springboot206security.bean.UserRoleBean;
import com.echo.springboot206security.entity.UserEntity;
import com.echo.springboot206security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("登陆对象:"+username);
        UserEntity userEntity = userRepository
                .findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("can not found username " + username);
        }

        return new UserRoleBean(userEntity);
    }


}


