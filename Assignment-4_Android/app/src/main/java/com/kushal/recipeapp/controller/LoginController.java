package com.kushal.recipeapp.controller;

import android.content.Context;

import com.kushal.recipeapp.api.ApiClient;
import com.kushal.recipeapp.api.ApiService;
import com.kushal.recipeapp.models.LoginRequest;
import com.kushal.recipeapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {
    private final ApiService apiService;

    public LoginController(Context context) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
    }

    public void loginUser(LoginRequest request, LoginCallback callback) {
        Call<LoginResponse> call = apiService.loginUser(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getMessage(), response.body().getToken());
                } else {
                    callback.onError("Invalid credentials or server error");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(String message, String token);
        void onError(String error);
    }
}
