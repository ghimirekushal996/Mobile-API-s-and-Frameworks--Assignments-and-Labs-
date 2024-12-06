package com.kushal.recipeapp.api;

import com.kushal.recipeapp.models.LoginRequest;
import com.kushal.recipeapp.models.LoginResponse;
import com.kushal.recipeapp.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth/register")
    Call<Void> registerUser(@Body User user);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}