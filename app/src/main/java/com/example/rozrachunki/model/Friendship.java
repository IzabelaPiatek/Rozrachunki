package com.example.rozrachunki.model;

public class Friendship {

    private Integer id;
    private Integer idUser;
    private Integer idFriend;

    public Friendship(Integer id, Integer idUser, Integer idFriend) {
        this.id = id;
        this.idUser = idUser;
        this.idFriend = idFriend;
    }

    public Friendship() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(Integer idFriend) {
        this.idFriend = idFriend;
    }
}
