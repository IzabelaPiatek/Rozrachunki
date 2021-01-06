package com.example.rozrachunki.classes;

public class UserAmountPOJO {
    private Integer id;
    private String username;
    private Integer amount = 0 ;

    public UserAmountPOJO(Integer id, String username, Integer amount) {
        this.id = id;
        this.username = username;
        this.amount = amount;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
