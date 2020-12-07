package com.example.rozrachunki.model;

public class Friendship {

    private Integer id;
    private Integer idUser;
    private Integer idFriend;
    private boolean accepted = false;
    private boolean hasAccount = false;
    private String username;
    private String email;
    private String phoneNumber;

    public Friendship(Integer id, Integer idUser, Integer idFriend, boolean accepted, boolean hasAccount, String username, String email, String phoneNumber) {
        this.id = id;
        this.idUser = idUser;
        this.idFriend = idFriend;
        this.accepted = accepted;
        this.hasAccount = hasAccount;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public boolean isHasAccount() { return hasAccount; }

    public void setHasAccount(boolean hasAccount) { this.hasAccount = hasAccount; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }
}
