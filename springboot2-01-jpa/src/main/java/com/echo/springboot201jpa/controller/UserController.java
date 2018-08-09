package com.echo.springboot201jpa.controller;

import com.echo.springboot201jpa.entity.UserEntity;
import com.echo.springboot201jpa.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    /**
     * http://localhost:8080/getall
     * @return
     */
    @RequestMapping("/getall")
    public List<UserEntity> getall() {
        List<UserEntity> list = userRepository.findAll();
        return list;
    }

    /**
     * http://localhost:8080/getonebyid?id=1
     * @param id
     * @return
     */
    @RequestMapping("/getonebyid")
    public UserEntity getoneByID(@RequestParam(name = "id",required = true) Integer id) {
        return userRepository.getOne(id);
    }

    /**
     * http://localhost:8080/getonebyname?username=testUpdate
     * @param username
     * @return
     */
    @RequestMapping("/getonebyname")
    public UserEntity getonrByName(@RequestParam(name = "username",required = true) String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * http://localhost:8080/?username=testUpdateupdate&new_username=echo
     * @param username
     * @param new_username
     * @return
     */
    @RequestMapping("/update")
    public UserEntity update(@RequestParam(required = true) String username,@RequestParam(required = true) String new_username) {
        userRepository.updateUsername(username,new_username);
//        userEntity.setUsername(new_username);
        return userRepository.findByUsername(new_username);

    }
}
