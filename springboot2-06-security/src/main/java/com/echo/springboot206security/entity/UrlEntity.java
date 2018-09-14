package com.echo.springboot206security.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "url")
@Proxy(lazy = false)
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    /**
     * @Lob 大对象，映射 MySQL 的 Long Text 类型
     */
    @Lob
    private String description;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "url_role",joinColumns = @JoinColumn(name = "url_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> rolelist;

    public UrlEntity() {
    }

    public UrlEntity(String url, String description, Set<RoleEntity> rolelist) {
        this.url = url;
        this.description = description;
        this.rolelist = rolelist;
    }

    @Override
    public String toString() {
        return "UrlEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", rolelistUrl=" + rolelist.toArray() +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RoleEntity> getRolelist() {
        return rolelist;
    }

    public void setRolelist(Set<RoleEntity> rolelist) {
        this.rolelist = rolelist;
    }
}
