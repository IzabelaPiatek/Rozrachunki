package com.example.rozrachunki.services;

import com.example.rozrachunki.classes.Friend;
import com.example.rozrachunki.classes.PaymentJson;
import com.example.rozrachunki.model.Payment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PaymentService {

    @POST("payments/add")
    Call<PaymentJson> add(@Body PaymentJson payment);


}
