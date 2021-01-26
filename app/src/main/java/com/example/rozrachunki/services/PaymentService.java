package com.example.rozrachunki.services;

import com.example.rozrachunki.classes.BorrowerJson;
import com.example.rozrachunki.classes.PaymentJson;
import com.example.rozrachunki.classes.PaymentWithOwnerJson;

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

    @GET("payments/get/{id}")
    Call<PaymentWithOwnerJson> get(@Path("id") Integer id);

    @POST("payments/delete/{id}")
    Call<Integer> delete(@Path("id") Integer id);

    @GET("payments/getBorrowers/{idPayment}")
    Call<ArrayList<BorrowerJson>> getBorrowers(@Path("idPayment") Integer idPayment);
}
