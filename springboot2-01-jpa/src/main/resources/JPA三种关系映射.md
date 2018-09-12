# Spring-Data-JPA 三种关系映射

## 一、 一对一 @OneToOne 注解 一对一关系映射
> 实体 People 用户
> 实体 Address 家庭地址  与 用户一对一对应

### 1.通过外键的方式实现
~~~
    //PeopleEntity.java
    //设置关系的维护端，当删除 people，会级联删除 address
    @OneToOne(cascade=CascadeType.ALL)
    //referencedColumnName  关联的外键，如果是主键的话可以省略，如果不是需要进行声名
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    
    //AddressEntity.java
    /**
    * 如果不需要根据Address级联查询People，可以注释掉
    */
    @OneToOne(mappedBy = "address", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
~~~

### 2.通过中间表实现 这个不太建议使用吧
> 主要在于新建一个表，表中存储两个实体类的主键，然后一一对应
> 和后面的多对多相似，但是一对一还是外键好用
~~~
    //People是关系的维护端 ，PeopleEntity.java
     @OneToOne(cascade=CascadeType.ALL)
     //通过关联表保存一对一的关系 这个注解相当于创建了一个中间表
     //joinColumns 为维护端  inverseJoinColumns为对应的数据表的主键
     //name="people_id"
     @JoinTable(name = "people_address",
                joinColumns = @JoinColumn(name="people_id"),
                inverseJoinColumns = @JoinColumn(name = "address_id"))
~~~

## 二、 一对多关系  @OneToMany 和 @ManyToOne 
> JPA使用@OneToMany和@ManyToOne来标识一对多的双向关联。
> 一端(Author)使用@OneToMany,多端(Article)使用@ManyToOne。
> 在JPA规范中，一对多的双向关系由多端(Article)来维护。就是说多端(Article)为关系维护端，
> 负责关系的增删改查。一端(Author)则为关系被维护端，不能维护关系
> 一端(Author)使用@OneToMany注释的mappedBy=”author”属性表明Author是关系被维护端。
> 多端(Article)使用@ManyToOne和@JoinColumn来注释属性 author,
> @ManyToOne表明Article是多端，@JoinColumn设置在article表中的关联字段(外键)

~~~
    //取出多端实例会出现could not initialize proxy - no Session的问题
    /**
    * 解决方法
    * 设置 fetch=FetchType.EAGER  热加载
    */
    @OneToMany(mappedBy = "author",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<ArticleEntity> articleList;
~~~

## 三、 多对多 @ManyToMany 
> JPA中使用@ManyToMany来注解多对多的关系，由一个关联表来维护。
> 这个关联表的表名默认是：主表名+下划线+从表名。(主表是指关系维护端对应的表,从表指关系被维护端对应的表)。
> 这个关联表只有两个外键字段，分别指向主表ID和从表ID。
> 字段的名称默认为：主表名+下划线+主表中的主键列名，从表名+下划线+从表中的主键列名。

**注意:**
> 1. 多对多关系中一般不设置级联保存、级联删除、级联更新等操作。
> 2. 可以随意指定一方为关系维护端
> 3. 多对多关系的绑定由关系维护端来完成
> 4. 多对多关系的解除由关系维护端来完成
> 5. 不能直接删除关系被维护端，需要先接触关系，才能够删除关系被维护端；可以直接删除关系维护端