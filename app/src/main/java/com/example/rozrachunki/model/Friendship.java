package com.example.rozrachunki.model;

public class Friendship {

    private Integer id;
    private Integer idUser;
    private Integer idFriend;
    private boolean accepted = false;

    public Friendship(Integer id, Integer idUser, Integer idFriend, boolean accepted) {
        this.id = id;
        this.idUser = idUser;
        this.idFriend = idFriend;
        this.accepted = accepted;
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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
