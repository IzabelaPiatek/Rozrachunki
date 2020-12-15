package com.example.rozrachunki.services;

import com.example.rozrachunki.model.Group;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GroupService {

    @GET("groups/getUserGroups/{idUser}")
    Call<ArrayList<Group>> getUserGroups(@Path("idUser") Integer idUser);
}
