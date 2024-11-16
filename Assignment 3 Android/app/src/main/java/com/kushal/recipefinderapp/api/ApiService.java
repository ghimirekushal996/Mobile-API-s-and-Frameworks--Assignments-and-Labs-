package com.kushal.recipefinderapp.api;



import com.kushal.recipefinderapp.model.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("recipes/")
    Call<List<Recipe>> getAllRecipes();
}
