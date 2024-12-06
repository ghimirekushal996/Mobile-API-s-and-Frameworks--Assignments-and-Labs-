package com.kushal.recipeapp.network_config;

import com.kushal.recipeapp.models.CreateRecipeModel;
import com.kushal.recipeapp.models.LoginRequestModel;
import com.kushal.recipeapp.models.LoginResponseModel;
import com.kushal.recipeapp.models.RecipeResponseModel;
import com.kushal.recipeapp.models.UserResponseModel;

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
    @POST("users/register")
    Call<Void> registerUser(@Body UserResponseModel user);

    @POST("users/login")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel loginRequest);

    @GET("routes/recipes")
    Call<List<RecipeResponseModel>> getRecipes(@Header("Authorization") String token);

    @POST("routes/recipes")
    Call<CreateRecipeModel> createRecipe(@Header("Authorization") String token, @Body CreateRecipeModel recipe);

    @PUT("routes/recipes/{id}")
    Call<CreateRecipeModel> updateRecipe(
            @Header("Authorization") String authorizationToken,
            @Path("id") String recipeId,
            @Body CreateRecipeModel updatedRecipe
    );
    @DELETE("routes/recipes/{id}")
    Call<Void> deleteRecipe(
            @Header("Authorization") String authorizationToken,
            @Path("id") String recipeId
    );
}
