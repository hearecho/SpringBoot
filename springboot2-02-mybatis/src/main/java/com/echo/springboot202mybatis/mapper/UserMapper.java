package com.echo.springboot202mybatis.mapper;

import com.echo.springboot202mybatis.domain.User;
import com.echo.springboot202mybatis.sqlprovider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface UserMapper {

    /**
     * 使用注解编写SQL。
     * @Select "select * from t_user"
     * @Update "update t_user set USERNAME=#{username} where USER_ID=#{id}"
     * @Delete "delete t_user where where USER_ID=#{id}"
     * @Insert "insert t_user values('hear', '123456', '2018-08-10 16:28:32')
     * @return List<User>
     */
    @Select("select * from t_user")
    List<User> getall();

    /**
     * 使用工具类构建复杂的sql语句
     * @param username
     * @return
     */
    @SelectProvider(type = UserSqlProvider.class,method = "getByUsername")
    User getByUsername(String username);

    /**
     * 上述两种方式都可以附加@Results注解来指定结果集的映射关系.
     * PS：如果符合下划线转驼峰的匹配项可以直接省略不写。
     */
    @Results({
            @Result(property = "userId", column = "USER_ID"),
            @Result(property = "username", column = "USERNAME"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "createtime", column = "CREATETIME")
    })
    @Select("select * from t_user")
    List<User> listSample();

    /**
     * 论什么方式,如果涉及多个参数,则必须加上@Param注解,否则无法使用EL表达式获取参数。
     */
    @Select("select * from t_user where username like #{username} and password like #{password}")
    User get(@Param("username") String username, @Param("password") String password);

}
