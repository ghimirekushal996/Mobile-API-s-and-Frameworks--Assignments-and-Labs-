package com.kushal.recipeapp.api;

import android.content.Context;

import com.kushal.recipeapp.sharedpreference.SharedPreferenceManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.2.34:3001/"; // Your base URL
    public static final String IMAGE_URL = "http://192.168.2.34:3001"; // Image URL
    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            // Get token from SharedPreferenceManager
                            SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(context);
                            String token = sharedPreferenceManager.getToken();

                            Request originalRequest = chain.request();
                            Request.Builder requestBuilder = originalRequest.newBuilder()
                                    .header("Authorization", token != null ? "Bearer " + token : "");

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}