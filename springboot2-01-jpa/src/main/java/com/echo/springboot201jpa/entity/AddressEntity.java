package com.echo.springboot201jpa.entity;


import org.hibernate.annotations.Proxy;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Proxy(lazy = false)
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "phone", nullable = true, length = 11)
    private String phone;
    @Column(name = "zipcode", nullable = true, length = 6)
    private String zipcode;
    @Column(name = "address", nullable = true, length = 100)
    private String address;

    /**
     * 如果不需要根据Address级联查询People，可以注释掉
     */
    @OneToOne(mappedBy = "address", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    private PeopleEntity people;

    public AddressEntity() {
    }

    public AddressEntity(String phone, String zipcode, String address, PeopleEntity people) {
        this.phone = phone;
        this.zipcode = zipcode;
        this.address = address;
        this.people = people;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", address='" + address + '\'' +
                ", people=" + people.getName() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PeopleEntity getPeople() {
        return people;
    }

    public void setPeople(PeopleEntity people) {
        this.people = people;
    }
}
