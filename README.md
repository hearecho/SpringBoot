# springboot2
### 1.数据访问

> springboot2 默认的数据源是 **Hibernate**，不再是以前的tomcat。

#### 1.1 springboot2-01-jpa
#### 1.springboot2-01-jpa
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
   - 关联属性相关的注解
