package com.example.rozrachunki.services;

import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.model.Group;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroupService {

    @GET("groups/getUserGroups/{idUser}")
    Call<ArrayList<GroupJson>> getUserGroups(@Path("idUser") Integer idUser);

    @POST("groups/add")
    Call<GroupJson> add(@Body Group group);
}
