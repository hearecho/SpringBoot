package com.echo.springboot201jpa.entity;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "game")
@Proxy(lazy = false)
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "gameList",fetch=FetchType.EAGER)
    private List<PlayerEntity> playerList;

    public GameEntity() {
    }

    public GameEntity(String name, List<PlayerEntity> playerList) {
        this.name = name;
        this.playerList = playerList;
    }

    @Override
    public String toString() {
        return "GameEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playerList=" + playerList.get(0).getUsername() +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerEntity> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<PlayerEntity> playerList) {
        this.playerList = playerList;
    }
}
