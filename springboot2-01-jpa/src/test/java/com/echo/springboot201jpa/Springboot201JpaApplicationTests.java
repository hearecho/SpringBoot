package com.echo.springboot201jpa;

import com.echo.springboot201jpa.entity.UserEntity;
import com.echo.springboot201jpa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot201JpaApplicationTests {

	@Autowired
	DataSource dataSource;

	@Autowired
    UserRepository userRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testData() throws SQLException {
		System.out.println(dataSource.getClass());
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		connection.close();
	}

    /**
     * 添加用户
     * @throws SQLException
     */
	@Test
    public void testInsert() throws SQLException {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UserEntity userEntity = new UserEntity();
//        userEntity.setCreateTime(new Date());
        userEntity.setUsername("echo");
        userEntity.setPassword("123456");
        userRepository.save(userEntity);
    }

    /**
     * 获取用户通过 id
     * @throws SQLException
     */
    @Test
    public void getoneById() throws SQLException {
	    UserEntity userEntity = userRepository.getOne(1);
	    System.out.println(userEntity.toString());
    }

    /**
     * 更新用户
     * @throws SQLException
     */
    @Test
    public void testUpdate() throws SQLException {
    	userRepository.updateUsername("echo","testupdate");
	    System.out.println(userRepository.getOne(1).toString());
    }



}
