package com.echo.springboot201jpa.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "author")
@Proxy(lazy = false)
public class AuthorEntity {
    /**
     * 主键自增长 GenerationType.IDENTITY
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "姓名不能为空")
    @Size(min=2, max=20)
    @Column(nullable = false, length = 20)
    private String name;

    /**
     * 级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
     * 拥有mappedBy注解的实体类为关系被维护端
     * mappedBy="author"中的author是Article中的author属性
     */
    @OneToMany(mappedBy = "author",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<ArticleEntity> articleList;

    public AuthorEntity() {
    }

    public AuthorEntity(@NotEmpty(message = "姓名不能为空") @Size(min = 2, max = 20) String name, List<ArticleEntity> articleList) {
        this.name = name;
        this.articleList = articleList;
    }

    @Override
    public String toString() {
        return "AuthorEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", articleList=" + articleList +
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

    public List<ArticleEntity> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleEntity> articleList) {
        this.articleList = articleList;
    }
}
