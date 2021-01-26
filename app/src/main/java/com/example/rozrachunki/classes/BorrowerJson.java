package com.example.rozrachunki.classes;

public class BorrowerJson {
    Integer id;
    String username;
    Integer paidByHim = 0;
    Integer owe;
    boolean settled = false;

    public BorrowerJson(Integer id, String username, Integer paidByHim, Integer owe, boolean settled) {
        this.id = id;
        this.username = username;
        this.paidByHim = paidByHim;
        this.owe = owe;
        this.settled = settled;
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

    public Integer getPaidByHim() {
        return paidByHim;
    }

    public void setPaidByHim(Integer paidByHim) {
        this.paidByHim = paidByHim;
    }

    public Integer getOwe() {
        return owe;
    }

    public void setOwe(Integer owe) {
        this.owe = owe;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}
