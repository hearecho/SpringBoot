package com.echo.springboot201jpa.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "article")
@Proxy(lazy = false)
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50) // 映射为字段，值不能为空
    private String title;

    /**
     * @Lob  大对象，映射 MySQL 的 Long Text 类型
     * fetch = FetchType.LAZY  表示懒加载
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    /**
     * 设置在article表中的关联字段(外键)
     * 可选属性optional=false,表示author不能为空。删除文章，不影响用户
     */
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="author_id")
    private AuthorEntity author;

    public ArticleEntity() {
    }

    public ArticleEntity(@NotEmpty(message = "标题不能为空") @Size(min = 2, max = 50) String title, @NotEmpty(message = "内容不能为空") @Size(min = 2) String content, AuthorEntity author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author=" + author.getName() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }
}
