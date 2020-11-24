package com.example.rozrachunki.classes;

import com.example.rozrachunki.model.User;

public class DataStorage {

    private static User user;

    public static void setUser(User _user) {
        user = _user;
    }

    public static User getUser() {
        return user;
    }
}
