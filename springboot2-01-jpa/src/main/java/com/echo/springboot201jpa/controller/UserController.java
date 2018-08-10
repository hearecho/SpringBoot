package com.echo.springboot201jpa.controller;

import com.echo.springboot201jpa.entity.UserEntity;
import com.echo.springboot201jpa.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,Object> getonrByName(@RequestParam(name = "username",required = true) String username) {
        //有可能出现返回值是多个，现在不可能了，因为设置了username不可重复
        Map<String,Object> map = new HashMap<>();
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity == null) {
            map.put("error","NOT FOUND");
        }else {
            map.put("success",userEntity);
        }
        return map;
    }

    /**
     * http://localhost:8080/update?username=testUpdateupdate&new_username=echo
     * @param username
     * @param new_username
     * @return
     */
    @RequestMapping("/update")
    public Map<String,Object> update(@RequestParam(required = true) String username,@RequestParam(required = true) String new_username) {
        //可以增加校验代码之类的,例如并没有这个用户
        Map<String,Object> map = new HashMap<>();
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity!=null) {
            map.put("status",1);
            userRepository.updateUsername(username,new_username);
            map.put("success",userRepository.findByUsername(new_username));
        }else {
            map.put("status",0);
            map.put("error","Not Contain this user");
        }
        return map;
    }

    /**
     * http://localhost:8080/insert?username=hear&password=123456789
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/insert")
    public Map<String,Object> insert(@RequestParam(required = true) String username,@RequestParam(required = true) String password) {
        Map<String,Object> map = new HashMap<>();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userRepository.save(userEntity);
        map.put("success","成功插入");
        return map;
    }


}
