package com.kushal.recipefinderapp.api;

import com.kushal.recipefinderapp.model.Recipe;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RecipeApi {
    @GET("/api/recipes")
    Call<List<Recipe>> getAllRecipes(@Header("Authorization") String token);
}
