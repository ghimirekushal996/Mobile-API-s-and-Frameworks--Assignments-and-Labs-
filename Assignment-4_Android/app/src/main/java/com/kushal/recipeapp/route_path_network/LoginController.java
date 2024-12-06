package com.kushal.recipeapp.route_path_network;

import android.content.Context;

import com.kushal.recipeapp.network_config.ApiClient;
import com.kushal.recipeapp.network_config.ApiService;
import com.kushal.recipeapp.models.LoginRequestModel;
import com.kushal.recipeapp.models.LoginResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {
    private final ApiService apiService;

    public LoginController(Context context) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
    }

    public void loginUser(LoginRequestModel request, LoginCallback callback) {
        Call<LoginResponseModel> call = apiService.loginUser(request);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess("Successfully log in!", response.body().getToken());
                } else {
                    callback.onError("Invalid credentials or server error");
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(String message, String token);
        void onError(String error);
    }
}
