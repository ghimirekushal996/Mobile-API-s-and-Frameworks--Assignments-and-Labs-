package com.kushal.recipeapp.api;

import com.kushal.recipeapp.models.AddRecipe;
import com.kushal.recipeapp.models.LoginRequest;
import com.kushal.recipeapp.models.LoginResponse;
import com.kushal.recipeapp.models.Recipe;
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

    @GET("recipe/")
    Call<List<Recipe>> getRecipes(@Header("Authorization") String token);

    @POST("recipe/create")
    Call<AddRecipe> createRecipe(@Header("Authorization") String token, @Body AddRecipe recipe);

    @PUT("recipe/{id}")  // Define the PUT request for updating a recipe
    Call<AddRecipe> updateRecipe(
            @Header("Authorization") String authorizationToken,
            @Path("id") String recipeId,  // Path parameter for recipe ID
            @Body AddRecipe updatedRecipe  // Request body with updated recipe data
    );
    @DELETE("recipe/{id}")
    Call<Void> deleteRecipe(
            @Header("Authorization") String authorizationToken,
            @Path("id") String recipeId
    );
}
