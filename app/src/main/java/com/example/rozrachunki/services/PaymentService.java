package com.example.rozrachunki.services;

import com.example.rozrachunki.classes.PaymentJson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PaymentService {

    @POST("payments/add")
    Call<PaymentJson> add(@Body PaymentJson payment);

    @POST("breakdowns/settleUpUser/{userId}/{borrowerUsername}")
    Call<Integer> settleUpUser(@Path("userId") Integer userId, @Path("borrowerUsername") String username);

    @GET("payments/getAllForGroup/{idGroup}")
    Call<ArrayList<PaymentJson>> getAllForGroup(@Path("idGroup") Integer idGroup);
}
