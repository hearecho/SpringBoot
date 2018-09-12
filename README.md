# springboot2
### 1.数据访问

> springboot2 默认的数据源是 **Hibernate**，不再是以前的tomcat。

#### 1.1 springboot2-01-jpa
> 使用[spring data jpa](https://docs.spring.io/spring-data/jpa/docs/2.1.0.RC1/reference/html/)模块。JPA也是基于ORM思想。

![](https://raw.githubusercontent.com/hearecho/springboot/master/img/springdata.PNG)

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
    url: jdbc:mysql://192.168.1.23:3306/java_web?useSSL=false&characterEncoding=utf-8&useUnicode=true
    username: root
    password: ssf971114
    driver-class-name: com.mysql.jdbc.Driver
  jpa:
    database: mysql
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    show-sql: true
    hibernate:
      ddl-auto: update
      naming:
        physical-strategy: org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy
#数据库如果要使用 InnoDB引擎就必须加入 org.hibernate.dialect.MySQL5Dialect
#ddl-auto 有四种模式
#1、validate- 加载hibernate时，验证创建数据库表结构。
#2、create- 每次加载hibernate，重新创建数据库表结构，这就是导致数据库表数据丢失的原因。
#3、create-drop 加载hibernate时创建，退出是删除表结构。
#4、update-级联更新 加载hibernate自动更新数据库结构。
~~~

##### 1.1.3 书写实体类-注解方式

1. 类级别的注解

   ~~~（java
   @Entity：
   1.映射实体类；必须含有这个注解；
   2.属性：name（可选） - 对应数据库的一个表；一般表明和实体类名都相同，一般省略
   @Table
   1.对应数据表的信息，可选
   2.属性：name（可选） - 表示表的名称，默认的表名和实体名称一致，只有在不一致的情况下才需要指定表名
   	   catalog（可选） - 表示Catalog 名称，默认为 Catalog("")
          schema（可选） - 表示Schema名称，默认为Schema("")
   ~~~

2. 属性级别的注解

   - 与主键相关的注解

     - **@Id** - 必须含有，定义数据库表的主键属性
     - **@GeneratedValue(strategy = GenerationType.? , generator="")** - 可选，用于定义主键生成策略
       - **strategy** - 表示主键生成策略
         - **GenerationType.AUTO** 根据底层数据库自动选择（默认），若数据库支持自动增长类型，则为自动增长
         - **GenerationType.INDENTITY** 根据数据库的Identity字段生成，支持DB2、MySQL、MS、SQL Server、SyBase与HyperanoicSQL数据库的Identity类型主键
         - **GenerationType.SEQUENCE** 使用Sequence来决定主键的取值，适合Oracle、DB2等支持Sequence的数据库，一般结合**@SequenceGenerator**使用（Oracle没有自动增长类型，只能用Sequence）
         - **GenerationType.TABLE**  使用指定表来决定主键取值，结合@TableGenerator使用
       - **generator** - 表示主键生成器的名称，这个属性通常和ORM框架相关 , 例如：Hibernate 可以指定 uuid 等主键生成方式 
     - **@SequenceGenerator** — 注解声明了一个数据库序列 
       - **name** - 表示该表主键生成策略名称，它被引用在@GeneratedValue中设置的“gernerator”值中
       - **sequenceName** - 表示生成策略用到的数据库序列名称
       - **initialValue** - 表示主键初始值，默认为0
       - **allocationSize** - 每次主键值增加的大小，例如设置成1，则表示每次创建新记录后自动加1，默认为50

   - 与非主键相关注解

     - **@Version** - 可以在实体bean中使用@Version注解，通过这种方式可添加对乐观锁定的支持（见参考链接）

     - **@Column** - 可将属性映射到列，使用该注解来覆盖默认值，@Column描述了数据库表中该字段的详细定义
       - **name** - 可选，表示数据库表中该字段的名称，默认情形属性名称一致
       - **nullable** - 可选，表示该字段是否允许为 null，默认为 true
       - **unique** - 可选，表示该字段是否是唯一标识，默认为 false
       - **length** - 可选，表示该字段的大小，仅对 String 类型的字段有效，默认值255
       - **insertable** - 可选，表示在ORM框架执行插入操作时，该字段是否应出现INSETRT语句中，默认为 true
       - **updateable** - 可选，表示在ORM 框架执行更新操作时，该字段是否应该出现在UPDATE 语句中，默认为 true。对于一经创建就不可以更改的字段，该属性非常有用，如对于 birthday 字段
       - **columnDefinition** - 可选，表示该字段在数据库中的实际类型。通常ORM 框架可以根据属性类型自动判断数据库中字段的类型，但是对于Date 类型仍无法确定数据库中字段类型究竟是 DATE,TIME 还是 TIMESTAMP. 此外 ,String 的默认映射类型为 VARCHAR, 如果要将 String 类型映射到特定数据库的 BLOB或 TEXT 字段类型，该属性非常有用

     - **@Basic** - 用于声明属性的存取策略：

       - @Basic(fetch=FetchType.EAGER)   即时获取（默认的存取策略）
       - @Basic(fetch=FetchType.LAZY)       延迟获取

     - **@Transient** - 可选，表示该属性并非一个到数据库表的字段的映射，ORM框架将忽略该属性，如果一个属性并非数据库表的字段映射，就务必将其标示为@Transient，否则ORM框架默认其注解为 @Basic

     - **@OneToOne**  

       描述一个一对一的关联  可选  fetch：表示抓取策略，默认为FetchType.LAZY  cascade：表示级联操作策略 

     - **@ManyToOne**  

       表示一个多对一的映射,该注解标注的属性通常是数据库表的外键  optional：是否允许该字段为null，该属性应该根据数据库表的外键约束来确定，默认为true  可选  fetch：表示抓取策略，默认为FetchType.EAGER  cascade：表示默认的级联操作策略，可以指定为ALL，PERSIST，MERGE，REFRESH和REMOVE中的若干组合，默认为无级联操作  targetEntity：表示该属性关联的实体类型。该属性通常不必指定，ORM框架根据属性类型自动判断targetEntity。 

     - **@OneToMany**  

       描述一个一对多的关联,该属性应该为集体类型,在数据库中并没有实际字段。  fetch：表示抓取策略,默认为FetchType.LAZY,因为关联的多个对象通常不必从数据库预先读取到内存  可选  cascade：表示级联操作策略,对于OneToMany类型的关联非常重要,通常该实体更新或删除时,其关联的实体也应当被更新或删除  例如：实体User和Order是OneToMany的关系，则实体User被删除时，其关联的实体Order也应该被全部删除 

     - **@ManyToMany**  

       描述一个多对多的关联.多对多关联上是两个一对多关联,但是在ManyToMany描述中,中间表是由ORM框架自动处理  可选  targetEntity:表示多对多关联的另一个实体类的全名,例如:package.Book.class  mappedBy:表示多对多关联的另一个实体类的对应集合属性名称  两个实体间相互关联的属性必须标记为@ManyToMany,并相互指定targetEntity属性,  需要注意的是,有且只有一个实体的@ManyToMany注解需要指定mappedBy属性,指向targetEntity的集合属性名称  利用ORM工具自动生成的表除了User和Book表外,还自动生成了一个User_Book表,用于实现多对多关联 

     - **@JoinColumn**  

       @JoinColumn和@Column类似,介量描述的不是一个简单字段,而一一个关联字段,例如.描述一个@ManyToOne的字段.  name:该字段的名称.由于@JoinColumn描述的是一个关联字段,如ManyToOne,则默认的名称由其关联的实体决定.  例如,实体Order有一个user属性来关联实体User,则Order的user属性为一个外键,  其默认的名称为实体User的名称+下划线+实体User的主键名称  @JoinTable(name = “student_teacher”, inverseJoinColumns = @JoinColumn(name = “tid”), joinColumns = @JoinColumn(name = “sid”))  可选  由第三张表来维护两张表的关系  name：是关系表的名字  joinColumns：自己这一端的主键  inverseJoinColumns：对方的主键 

     - **@Embedded**  

       @Embedded将几个字段组合成一个类,并作为整个Entity的一个属性.  例如User包括id,name,city,street,zip属性.  我们希望city,street,zip属性映射为Address对象.这样,User对象将具有id,name和address这三个属性.  Address对象必须定义为@Embededable 

   - 关联属性相关的注解

     官方文档

3. 代码

   ~~~java
   @Entity
   @Table(name = "w_user")
   @Proxy(lazy = false)
   //防止 出现no-session的bug，不使用懒加载
   public class UserEntity {
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       private Integer id;
       @Column(unique = true)
       private String username;
       @Column
       private String password;
       @Temporal(TemporalType.TIMESTAMP)
       @Column(name = "UPDATE_TIME")
       //设置默认值
       private Date createTime = new Date();
   
       //getter和setter方法
   }
   ~~~

##### 1.1.4 书写 Repository接口，创建数据库访问的Dao层

> **只需要继承一种适应的接口的便可**
>
> Respository<T,ID extends Serializable>:统一接口
>
> RevisionRepository<T,ID extends Serializable,N extends Number & Comparable<N>> 基于乐观锁机制
>
> CrudRepository<T,ID extends Serializable>:基本的CRUD操作
>
> PagingAndSortingRespository<T,ID extends Serializable>:基本CRUD及分页
>
> JpaRepository<T,ID extends Serializable>：CRUD操作

~~~java
//继承JpaRepository完成对数据库的操作
/**
 * public interface JpaRepository<T, ID>
 * T  为实体类的类名
 * ID 为实体类中主键的类型
 */
public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    /**
     * 可以自己定义一些函数
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
    @Modifying//有这个注解的函数，返回值 只能是void 或者是 Interger等
    @Transactional
    @Query("update UserEntity u set u.username=:new_username where u.username=:username ")
    void updateUsername(@Param("username") String username,@Param("new_username") String new_username);
}
~~~

##### 1.1.5 书写Controller，Web层面

~~~java
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
     * http://localhost:8080/update?username=testUpdateupdate&new_username=echo
     * @param username
     * @param new_username
     * @return
     */
    @RequestMapping("/update")
    public UserEntity update(@RequestParam(required = true) String username,@RequestParam(required = true) String new_username) {
        //可以增加校验代码之类的
        userRepository.updateUsername(username,new_username);
        return userRepository.findByUsername(new_username);
    }
}
~~~

#### 1.2 springboot2-02-mybatis

> springboot2整合mybatis；
>
> 相比较**Hibernate+jpa**来说，我更喜欢mybatis，因为对于mysql语句更加容易修改，自定义。
>
> 使用注解的方式；一切由注解解决；

##### 1.2.1 依赖添加

~~~xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>1.3.2</version>
</dependency>
~~~

##### 1.2.2 数据库配置

~~~yml
spring:
  datasource:
    url: jdbc:mysql://192.168.1.23:3306/java_web?useSSL=false&characterEncoding=utf-8&useUnicode=true
    username: root
    password: ssf971114
    driver-class-name: com.mysql.jdbc.Driver
mybatis:
  configuration:
    map-underscore-to-camel-case: true 
~~~

##### 1.2.3 实体类书写，sql文件引入；

~~~mysql
public class User {
    private String userId;
    private String username;
    private String password;
    private Date createtime;
    ////Getters & Setters
}    
~~~

~~~mysql
sql 文件
USE `java_web`;
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `USER_ID`  integer(50) NULL ,
  `USERNAME`  varchar(50) NULL ,
  `PASSWORD`  varchar(255) NULL ,
  `CREATETIME`  datetime NULL 
   PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8;

INSERT INTO `t_user` VALUES ('1', 'echo', '123456', '2018-08-10 16:28:31')
INSERT INTO `t_user` VALUES ('2', 'hear', '123456', '2018-08-10 16:28:32')
~~~

##### 1.2.4 编写数据层代码

> 编写UserMapper接口使用mybatis的注解和使用SqlProvider

~~~java
//UserMapper.java
/**
* @Mapper 
* 可以在启动类中使用@MapperScan 来取代@Mapper 这样可以不用在每一个Mapper接口都进行注解
* ""内容是 Mapper接口所在的包
* @MapperScan("com.echo.springboot202mybatis.mapper")
* public class Springboot202MybatisApplication {...}
*/
@Mapper
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
~~~

> SqlProvider 根据复杂的业务需求来动态生成SQL.在UserMapper中的@SelectProvider注解中进行调用。
>
> 同时还有其他CRUD四种Provider

~~~java
/**
 * UserSqlProvider.java
 * 目标：使用Java工具类来替代传统的XML文件.(例如：UserSqlProvider.java <-- UserMapper.xml)
 */
public class UserSqlProvider {
    /**
     * 1.在工具类的方法里,可以自己手工编写SQL。
     */
    public String getByUsername(String username) {
        return "select * from t_user where username =#{username}";
    }

    /**
     * 2.可以根据官方提供的API来编写动态SQL。
     */
    public String getBadUser(@Param("username") String username, @Param("password") String password) {
        return new SQL() {{
            SELECT("*");
            FROM("t_user");
            if (username != null && password != null) {
                WHERE("username like #{username} and password like #{password}");
            } else {
                WHERE("1=2");
            }
        }}.toString();
    }
}
~~~

##### 1.2.5 Web层面

~~~java
@RestController
@RequestMapping("/user")
public class UserController {

    @SuppressWarnings("all")
    @Autowired
    UserMapper userMapper;
    /***
     * http://localhost:8080/user/getall
     * @return
     */
    @GetMapping("/getall")
    public List<User> getall() {
        return userMapper.getall();
    }
    /**
     * http://localhost:8080/user/getone?username=echo
     * @param username
     * @return
     */
    @GetMapping("/getone")
    public User getByUsername(@RequestParam(name = "username",required = true) String username) {
        return userMapper.getByUsername(username);
    }
}
~~~

#### 1.3 springboot2-03-druid

> springboot2整合druid；java配置真好用。

##### 1.3.1 依赖注入

> 只是简单的使用 druid数据源，并不会对mybatis 造成影响，如果要继续整合，把mybatis加上就可以了。

~~~xml
<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>druid</artifactId>
	<version>1.1.10</version>
</dependency>
~~~

##### 1.3.2 配置属性

> 全部为自定义属性，会被java配置文件@Value调用

~~~yml
#配置属性使用application.yml
connection:
  url: jdbc:mysql://192.168.1.24:3306/java_web?useUnicode=true&characterEncoding=utf-8&useSSL=false
  username: root
  password: ssf971114
druid:
  # 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
  initialSize: 1
  #    最小连接池数量
  minIdle: 1
  # 最大连接池数量
  maxActive: 10
  #    配置获取连接等待超时的时间
  maxWait: 10000
  #  配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
  timeBetweenEvictionRunsMillis: 60000
  #  配置一个连接在池中最小生存的时间，单位是毫秒
  minEvictableIdleTimeMillis: 300000
  #  验证连接有效与否的SQL，不同的数据配置不同
  validationQuery: select 1
  #  建议配置为true，不影响性能，并且保证安全性。
  #  申请连接的时候检测，如果空闲时间大于
  #  timeBetweenEvictionRunsMillis，
  #  执行validationQuery检测连接是否有效。
  testWhileIdle: true
  #  申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
  #  这里建议配置为TRUE，防止取到的连接不可用
  testOnBorrow: true
  #  归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
  testOnReturn: false
  #  是否缓存preparedStatement，也就是PSCache。
  #  PSCache对支持游标的数据库性能提升巨大，比如说oracle。
  #  在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。
  #  作者在5.5版本中使用PSCache，通过监控界面发现PSCache有缓存命中率记录，
  #  该应该是支持PSCache。
  #  打开PSCache，并且指定每个连接上PSCache的大小
  poolPreparedStatements: true
  maxPoolPreparedStatementPerConnectionSize: 20
  #  属性类型是字符串，通过别名的方式配置扩展插件，
  #  常用的插件有：
  #  监控统计用的filter:stat
  #  日志用的filter:log4j
  #  防御sql注入的filter:wall
  filters: stat,wall
  # 访问的用户名
  loginUsername: admin
  # 访问的密码
  loginPassword: admin
~~~

##### 1.3.3 书写java配置文件

> 使用@Configuration注解，配置类；

~~~java
@Configuration
public class DruidConfig {
    private static final Log log = LogFactory.getLog(DruidConfig.class);

    @Value("${connection.url}")
    private String connectionUrl;
    @Value("${connection.username}")
    private String username;
    @Value("${connection.password}")
    ......
    //    配置数据源
    @Bean(name = "basisDataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource initDataSource() {
        log.info("初始化DruidDataSource");
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName("com.mysql.jdbc.Driver");
        dds.setUrl(connectionUrl);
        ......
        try {
            dds.setFilters(filters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dds;
    }

    /**
     *  配置Druid的监控
     *  配置一个管理后台
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //设置登录查看信息的账号密码.
        .....
    }
    /**
     * 配置监控的filter
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        ....
    }
}
~~~

##### 1.3.4 [访问主页](http://localhost:8080/druid)

![](https://raw.githubusercontent.com/hearecho/springboot/master/img/druidlogin.PNG)

![](https://raw.githubusercontent.com/hearecho/springboot/master/img/druidadmin.PNG)

### 2.[springboot2 配置](https://docs.spring.io/spring-boot/docs/2.0.4.RELEASE/reference/htmlsingle/#boot-features-external-config)

> Springboot2 采用的全局配置模式；
>
> 配置文件名字是固定的 ，不使用以前的xml，json之类的配置文件；
>
> 1. appliaction.properties
> 2. application.yml
>
> 之后使用注解的方式将配置文件注入；

#### 2.1 yml配置文件语法

##### 2.1.1 基本语法

> key:(空格)value
>
> 空格必须有，利用缩进换行来表示层级关系，缩进一样的是同一个层级的；
>
> 属性和值也是大小写敏感

~~~yml
#示例
server:
  port: 80
  servlet:
    path: /demo
~~~

##### 2.1.2 值的写法

1. 字面量

   > 字面直接来写，字符串默认不用加上单引号或者双引号；
   >
   > 1. 字符串加双引号，转译
   >
   >    ~~~ ==java
   >    name: "zhangsan\nlisi" ==> "zhangsan换行lisi"
   >    ~~~
   >
   > 2. 字符串加单引号，不转译
   >
   >    ~~~java
   >    name: 'zhangsan\nlisi' ==> "zhangsan\nlisi"
   >    ~~~

2. 对象，Map(属性和值) 键值对

   > 1. key: value 方式 注意冒号后买你的空格
   >
   >    ~~~java
   >    friends:
   >    	lastName: zhangsan
   >    	age: 20
   >    ~~~
   >
   > 2. 行内写法 注意冒号后买你的空格
   >
   >    ~~~java
   >    friends: {lastName: zhangsan,age: 18}
   >    ~~~
   >
   >    

3. 数组（List，Set）： 用 - 表示数组中的一个元素

   > 1. 一般写法
   >
   >    ~~~yml
   >    pets: 
   >     - cat
   >     - dog
   >     - pig
   >    ~~~
   >
   > 2. 行内写法
   >
   >    ~~~yml
   >    pets: [cat,dog,pig]
   >    ~~~

#### 2.2 prperties配置文件语法

> 相比较yml 不直观，重复较多
>
> ~~~java
> person.name = zhangsan
> person.age = 20
> ~~~

#### 2.3配置文件注入

> 导入[配置文件处理器](https://docs.spring.io/spring-boot/docs/2.0.4.RELEASE/reference/html/configuration-metadata.html#configuration-metadata-annotation-processor)，编写配置文件的时候有提示
>
> ~~~xml
> <dependency>    
>     <groupId>org.springframework.boot</groupId>    
>     <artifactId>spring‐boot‐configuration‐processor</artifactId>    
>     <optional>true</optional> 
> </dependency> 
> ~~~
>
> 示例配置文件,两种配置文件可以同时存在；相同属性优先properties文件
>
> ~~~yml
> #application.properties
> person.age=12
> person.boss=false
> person.last-name=里斯
> person.maps.k1=v1
> person.maps.k2=v2
> person.lists=1,2,3
> #application.yml
> server:
>   port: 80
>   servlet:
>     path: /demo
> person:
>   last-name: 里斯
>   age: 12
>   maps: {k1: v1,k2: v2}
>   lists: [a,b,v]
>   boss: false
> ~~~

##### 2.3.1 @ConfigurationProperties 注解

> 将文本的所有属性和配置文件中 的相关配置进行绑定； 
>
> 只有这个组件是容器中的组件，才能提供到容器中 ；
>
> 专门编写了一个javaBean和配置文件进行映射，直接使用 @ConﬁgurationProperties

~~~java
@Component
@ConfigurationProperties(prefix = "person")
//针对配置文件person下面的属性进行映射
public class Person {
    private String lastName;
    private Integer age;
    private Boolean boss;
    private Map<String,Object> maps;
    private List<Object> lists;

    //getter 和 setter  方法
    //不能省略
}
~~~

##### 2.3.2 @Value注解

> @Value注解进行单个注解，不要getter和setter方法；
>
> 不支持复杂类型的映射例如：类和map图等；
>
> 经常在某一个业务中使用到配置文件的一个值的时候才会使用

~~~java
@Component
public class PersonValue {
    @Value("${person.last-name}")
    private String lastName;
    @Value("#{11*2}")
    private Integer age;
    @Value("${person.boss}")
    private Boolean boss;
//    不支持复杂类型,类 map等
//    @Value("${person.maps}")
    private Map<String,Object> maps;
    @Value("${person.lists}")
    private List<Object> lists;
	//不需要getter和setter
}
~~~

##### 2.3.3 两种注解的区别

|                    | @Configurationproperties |  @Value  |
| :----------------: | :----------------------: | :------: |
|        功能        |   批量诸如配置文件属性   | 单个指定 |
|  松散绑定（语法）  |           支持           |  不支持  |
|   spEl：#{11*2}    |          不支持          |   支持   |
| JSR303校验：@Email |           支持           |  不支持  |
|  复杂类型：类，图  |           支持           |  不支持  |

##### 2.3.4 其他注解

1. 加载指定的properties配置文件：@PropertySource

   ~~~java
   //我们可以建立一个专门用于数据库连接池的配置文件 datasource.properties
   // 前提是先导入数据库连接依赖
   connection.url=jdbc:mysql://192.168.1.24:3306/java_web?useUnicode=true&characterEncoding=utf-8&useSSL=false
   connection.username=root
   connection.root=ssf971114
   connection.driver-class-name:com.mysql.jdbc.Driver
   //对应的配置文件注入
   @PropertySource(value={"classpath:datasource.properties"})
   @Component
   @ConfigurationProperties(prefix = "connection")
   public class DuridConfig() {
       private String username;
       ....
   }
   ~~~

2. @ImportResource : 导入Spring配置文件，并且让这个配置文件生效

   > 这是以前的spring用的xml写法，使用这个注解注入到启动类中；一般用于配置组件；
   >
   > springboot使用全注解方式@Conﬁguration+@Bean来替代这种方式；

   ~~~java
   //HelloService组件
   public class HelloService {
   }
   //MyAppConfig.java配置类
   @Configuration
   public class MyAppConfig {
       @Bean
       public HelloService helloService() {
           System.out.println("配置类给容器添加了HelloService组件");
           return new HelloService();
       }
   }
   //测试方法
   @Autowired
   ApplicationContext ioc;
   @Test
   public void testHelloService(){
   	//Bean  参数是方法名
       boolean b = ioc.containsBean("helloService");
       System.out.println(b);
   }
   ~~~

#### 2.4配置文件占位符

##### 2.4.1 随机数

> \${random.value} 、\${random.int}、\${random.long} 、\${random.int(10)}  、\${random.int[100,200]}
>
> 在配置文件中使用

~~~java
person.age=${random.int(10,30)}
person.boss=false
person.last-name=里斯${random.uuid}
person.maps.k1=${random.int}
person.maps.k2=${person.last-name}
#没有声名或者在后面声名的数据需要设置默认值
person.lists=1,2,3,${person.no: 4}
~~~

#### 2.5 Profile 配置不同环境下不同的配置

> 主配置文件编写的时候，文件名可以是 application{proﬁle}.properties/yml 
>
> - application.properties
> - application-dev.properties
> - application-prod.properties
>
> 默认使用的是application.properties配置文件，所以可以在该配置文件中指定用哪个配置文件
>
> ~~~properties
> spring.profiles.active=dev
> ~~~
>
> 一般yml配置文件推荐使用文档块

##### 2.5.1 YAML文档块

~~~yaml
server:
 port: 80
spring:
 profiles:
  active: dev
  
---

server:
 port: 8081
spring:
 profiles: dev
 
---

server:
 port: 81
spring:
 profiles: prod
~~~

##### 2.5.2启动方式

1. 在配置文件中激活，常用

2. 命令行：

   ~~~shell
   --spring.profiles.active=dev
   ~~~

3. 打包成jar包后，用命令行启动，优先级大于配置文件

   ~~~shell
   java -jar springboot2-04-config.jar --spring.profiles.active=dev
   ~~~

4. 虚拟机参数，没用过

   ~~~shell
   -Dspring.profiles.active=dev
   ~~~

#### 2.6 加载配置文件的位置

> SpringBoot启动扫描以下位置的appliaction.properties 或者 application.yml文件作为配置文件
>
> - ./config/
> - ./
> - classpath:/config/
> - classpath:/
>
> 优先级从高到低，高优先级会覆盖低优先级的相同配置；配置互补
>
> 也可以通过spring.conﬁg.location来改变默认配置
>
> ~~~properties
> server.servlet.context-path=***
> ~~~
>
> [引入外部配置官方文档](https://docs.spring.io/spring-boot/docs/2.0.1.RELEASE/reference/htmlsingle/#boot-features-external-config)

### 3.springboot2 日志

#### 3.1日志框架

> 日志框架一般调用的为抽象层次
>
> | 日志抽象层                                                   | 日志实现                         |
> | ------------------------------------------------------------ | -------------------------------- |
> | ~~JCL(Jakarta Commons Logging)~~ SLF4j(Simple Logging Facade for Java) ~~jboss-logging~~ | ~~Log4j~~,~~JUL~~,Log4j2 Logback |
>
> springboot 推荐使用 SLF4j和logback
>
> 注意：后续会有不同的日志框架之间的整合

#### 3.2 SLF4j的使用 

##### 3.2.1 简单使用

> 每个日志框架的实现框架都有自己的配置文件。使用slf4j以后，配置文件还是 做成日志实现框架本身的配置文件；

~~~java
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
 
public class HelloWorld {  
    public static void main(String[] args) {    
        Logger logger = LoggerFactory.getLogger(HelloWorld.class);    
        logger.info("Hello World");  
    } 
}
~~~

#### 3.3 统一日志框架

> 同意日志框架的主要部分；
>
> 1. 将系统中其他日志框架先排除
> 2. 用中间包替换原有的日志框架
> 3. 导入slf4j的实现

#### 3.4 日志的使用

> 日志的级别：trace-debug-info-warn-error，（从低到高）
>
> SpringBoot默认给的是info级别，如果没指定就是默认的root级别 ，日志就只会在这个级别以后的高级别生效
>
> 修改配置:
>
> ~~~properties
> logging.level.com.echo.springboot205log=trace
> #不指定path就是当前目录下生成springboot.log 
> #logging.file=springboot.log 
> #当前磁盘下根路径创建spring文件中log文件夹，使用spring.log作为默认 
> logging.path=/spring/log 
> #控制台输出的日志格式 日期 + 线程号 + 靠左 级别 +全类名50字符限制+消息 +换行 
> logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n 
> #指定文件中日志输出的格式 
> logging.pattern.ﬁle=xxx
> #日志输出格式:
> #控制台输出的日志格式 
> #%d：日期 #%thread：线程号 
> #%-5level：靠左 级别 
> #%logger{50}：全类名50字符限制,否则按照句号分割 
> #%msg：消息+换行 
> #%n：换行 
> ~~~
>

### 4.简单的web demo 显示爬虫内容

> 使用springboot2自带的hibernate与数据库进行连接；使用thymeleaf作为前端的视图解析器；
>
> 