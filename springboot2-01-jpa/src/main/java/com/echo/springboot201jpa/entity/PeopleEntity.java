package com.echo.springboot201jpa.entity;


import org.hibernate.annotations.Proxy;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "people")
@Proxy(lazy = false)
public class PeopleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = true, length = 20)
    private String name;
    @Column(name = "sex", nullable = true, length = 1)
    private String sex;
    @Column(name = "birthday", nullable = true)
    private Timestamp birthday;

    /**
     * 1.People是关系的维护端，当删除 people，会级联删除 address
     * 2.people中的address_id字段参考address表中的id字段
     * 3.关联的实体的主键一般是用来做外键的。但如果此时不想主键作为外键，则需要设置referencedColumnName属性。
     *   当然这里关联实体(Address)的主键 id 是用来做主键，所以这里第20行的 referencedColumnName = “id” 实际可以省略。
     */
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    public PeopleEntity() {
    }

    public PeopleEntity(String name, String sex, Timestamp birthday, AddressEntity address) {
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
    }

    @Override
    public String toString() {
        return "PeopleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", address=" + address.toString() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
