package com.example.rozrachunki.remote;

import com.example.rozrachunki.services.UserService;

public class ApiUtils {

    public static final String BASE_URL="http://192.168.2.3:9090/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
