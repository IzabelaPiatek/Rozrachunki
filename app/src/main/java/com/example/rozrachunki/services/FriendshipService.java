package com.example.rozrachunki.services;

import com.example.rozrachunki.model.Friendship;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FriendshipService {

    @GET("friends/getUserFriends/{idUser}")
    Call<ArrayList<Friendship>> getUserFriends(@Path("idUser") Integer idUser);

    @POST("friends/delete/{friendshipId}")
    Call<Integer> delete(@Path("friendshipId") Integer friendshipId);

    @POST("friends/add")
    Call<Friendship> add(@Body Friendship friendship);
}
