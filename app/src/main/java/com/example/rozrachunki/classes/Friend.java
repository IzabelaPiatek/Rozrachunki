package com.example.rozrachunki.classes;

public class Friend {

    Integer id;
    String username;
    Integer owesYou;
    Integer youOwe;
    Integer owe;
    byte[] image;

    public Friend(Integer id, String username, Integer owesYou, Integer youOwe, byte[] image) {
        this.id = id;
        this.username = username;
        this.owesYou = owesYou;
        this.youOwe = youOwe;
        this.owe = owesYou + youOwe;
        this.image = image;
    }

    public Friend() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getOwesYou() {
        return owesYou;
    }

    public void setOwesYou(Integer owesYou) {
        this.owesYou = owesYou;
    }

    public Integer getYouOwe() {
        return youOwe;
    }

    public void setYouOwe(Integer youOwe) {
        this.youOwe = youOwe;
    }

    public Integer getOwe() {
        return owe;
    }

    public void setOwe(Integer owe) {
        this.owe = owe;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
