package com.example.rozrachunki.services;

import com.example.rozrachunki.classes.GroupJson;
import com.example.rozrachunki.model.Group;
import com.example.rozrachunki.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GroupService {

    @GET("groups/getUserGroups/{idUser}")
    Call<ArrayList<GroupJson>> getUserGroups(@Path("idUser") Integer idUser);

    @POST("groups/add/{idUser}")
    Call<GroupJson> add(@Body Group group, @Path("idUser") Integer idUser);

    @GET("groupmembers/getGroupMembers/{idGroup}")
    Call<ArrayList<User>> getGroupMembers(@Path("idGroup") Integer idGroup);

    @GET("groups/get/{idGroup}")
    Call<GroupJson> getGroup(@Path("idGroup") Integer idGroup);

    @POST("groups/delete/{idGroup}")
    Call<Integer> delete(@Path("idGroup") Integer idGroup);

    @HTTP(method = "POST", path = "groups/updateGroup", hasBody = true)
    Call<Integer> updateGroup(@Body Group group);

}
