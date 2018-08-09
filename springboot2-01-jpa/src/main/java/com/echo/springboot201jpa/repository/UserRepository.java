package com.echo.springboot201jpa.repository;

import com.echo.springboot201jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

//继承JpaRepository完成对数据库的操作

/**
 * public interface JpaRepository<T, ID>
 * T  为实体类的类名
 * ID 为实体类中主键的类型
 */

public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    /**
     * 通过用户名获取信息
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);

    /**
     * 使用 jpql 语句编写操作函数
     * sql语句:  select name ,age from user;
     * jpql语句: select u.name,u.age from User u;
     * User 为 实体类名称
     */
    @Modifying
    @Transactional
    @Query("update UserEntity u set u.username=:new_username where u.username=:username ")
    void updateUsername(@Param("username") String username,@Param("new_username") String new_username);
}
