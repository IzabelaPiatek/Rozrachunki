package com.example.rozrachunki.services;

import com.example.rozrachunki.classes.PaymentJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentService {

    @POST("payments/add")
    Call<PaymentJson> add(@Body PaymentJson payment);
}
