package com.example.rozrachunki.services;

import com.example.rozrachunki.classes.Friend;
import com.example.rozrachunki.model.Friendship;
import com.example.rozrachunki.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FriendshipService {

    @GET("friends/getUserFriends/{idUser}")
    Call<ArrayList<Friend>> getUserFriends(@Path("idUser") Integer idUser);

    @POST("friends/delete/{friendshipId}")
    Call<Integer> delete(@Path("friendshipId") Integer friendshipId);

    @POST("friends/add/{userId}")
    Call<Friendship> add(@Path("userId") Integer userId, @Body User user);
}
