package com.example.rozrachunki.services;

import com.example.rozrachunki.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

public interface UserService {

    @GET("userslogin/{username}/{password}")
    Call<User> login(@Path("username") String username,
                     @Path("password") String password);

    //@GET("users/save")
    @HTTP(method = "POST", path = "users/save", hasBody = true)
    Call<User> register(@Body User user);

    @GET("mobile/hello")
    Call<User> message();

    @HTTP(method = "POST", path = "users/updateUserData", hasBody = true)
    Call<Integer> updateUserData(@Body User user);

    @GET("users/get/{username}")
    Call<User> get(@Path("username") String username);

//    @HTTP(method = "POST", path = "send/{instructor}", hasBody = true)
//    Call<Message> sendCoordinates(@Body ArrayList<LocationToSend> coordinates,
//                                  @Path("instructor") Integer currentLoggedInstructorId);
//    @GET("timetable/{id}")
//    Call<ArrayList<TimetableJson>> getTodayTimetable(@Path("id") Integer instructorId);
}