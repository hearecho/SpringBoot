# springboot2
### 1.数据访问

> springboot2 默认的数据源是 **Hibernate**，不再是以前的tomcat。

#### 1.1 springboot2-01-jpa

> 使用[spring data jpa](https://docs.spring.io/spring-data/jpa/docs/2.1.0.RC1/reference/html/)模块。JPA也是基于ORM思想。

![](img/springdata.png)

##### 1.1.1 pom依赖导入

~~~xml
<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<scope>runtime</scope>
</dependency>
~~~

##### 1.1.2 数据库配置

~~~yml
spring:
  datasource:
    url: jdbc:mysql://192.168.1.23:3306/java_web?useSSL=false&characterEncoding=utf-8
    username: root
    password: ssf971114
    driver-class-name: com.mysql.jdbc.Driver
  jpa:
    database-platform: mysql
    show-sql: true
    hibernate:
      ddl-auto: update
    properties:
          hibernate:
            dialect: org.hibernate.dialect.MySQL5Dialect
#数据库如果要使用 InnoDB引擎就必须加入 org.hibernate.dialect.MySQL5Dialect
#ddl-auto 有四种模式
#1、validate- 加载hibernate时，验证创建数据库表结构。
#2、create- 每次加载hibernate，重新创建数据库表结构，这就是导致数据库表数据丢失的原因。
#3、create-drop 加载hibernate时创建，退出是删除表结构。
#4、update-级联更新 加载hibernate自动更新数据库结构。
~~~

