package com.example.rozrachunki.services;

import com.example.rozrachunki.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FriendshipService {

    @GET("friends/getUserFriends/{idUser}")
    Call<ArrayList<User>> getUserFriends(@Path("idUser") Integer idUser);

    @POST("friends/delete/{idUser}/{idFriend}")
    Call<Integer> delete(@Path("idUser") Integer idUser, @Path("idFriend") Integer idFriend);
}
