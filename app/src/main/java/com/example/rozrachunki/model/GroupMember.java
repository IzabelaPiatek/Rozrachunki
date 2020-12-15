package com.example.rozrachunki.model;

public class GroupMember {
    private Integer id;

    private Integer idUser;

    private Integer idGroup;

    public void GroupMember(Integer id, Integer idUser, Integer idGroup) {
        this.id = id;
        this.idUser = idUser;
        this.idGroup = idGroup;
    }

    public void GroupMember() {}

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

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }
}
