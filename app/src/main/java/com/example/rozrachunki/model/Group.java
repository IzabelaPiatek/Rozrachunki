package com.example.rozrachunki.model;

import java.sql.Blob;

public class Group {
    private Integer id;

    private String name;

    private Integer type;

    private boolean settled;

    private Blob image;

    public Group(Integer id, String name, Integer type, boolean settled, Blob image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.settled = settled;
        this.image = image;
    }

    public void Group() {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
