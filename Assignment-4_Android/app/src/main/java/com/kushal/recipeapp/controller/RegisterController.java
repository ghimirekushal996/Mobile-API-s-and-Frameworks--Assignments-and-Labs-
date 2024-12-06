package com.kushal.recipeapp.controller;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterController {
    private final ApiService apiService;

    public RegisterController(Context context) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
    }

    public void registerUser(User user, RegisterCallback callback) {
        Call<Void> call = apiService.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("User registered successfully");
                } else {
                    callback.onError("Failed to register user");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface RegisterCallback {
        void onSuccess(String message);

        void onError(String error);

        void onUserExists(String message);
    }
}
