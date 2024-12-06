package com.kushal.recipeapp.screen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kushal.recipeapp.R;
import com.kushal.recipeapp.adapter.MyRecipeAdapter;
import com.kushal.recipeapp.network_config.ApiClient;
import com.kushal.recipeapp.network_config.ApiService;
import com.kushal.recipeapp.models.RecipeResponseModel;
import com.kushal.recipeapp.sharedpreference.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private MyRecipeAdapter adapter;
    FloatingActionButton floatingActionButton;

    Button btnLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnLog = findViewById(R.id.logout);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUpdateRecipeActivity.class);
                startActivity(intent);
            }
        });

        fetchRecipes();
    }

    private void fetchRecipes() {
        // Get the token from shared preferences
        SharedPreferenceManager preferenceManager = new SharedPreferenceManager(this);
        String token = preferenceManager.getToken();

        if (token == null) {
            Toast.makeText(this, "Token not found. Please log in.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the ApiService using ApiClient
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);

        // Call the API to fetch recipes with the Authorization token
        Call<List<RecipeResponseModel>> call = apiService.getRecipes(token);

        call.enqueue(new Callback<List<RecipeResponseModel>>() {
            @Override
            public void onResponse(Call<List<RecipeResponseModel>> call, Response<List<RecipeResponseModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Get the list of recipes from the response
                    List<RecipeResponseModel> recipes = response.body();

                    // Initialize the adapter with the recipes list
                    adapter = new MyRecipeAdapter(MainActivity.this, recipes, apiService);

                    // Set the adapter for the RecyclerView
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.e(TAG, "Failed to fetch recipes: " + response.code());
                    Toast.makeText(MainActivity.this, "Failed to fetch recipes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RecipeResponseModel>> call, Throwable t) {
                Log.e(TAG, "Error fetching recipes", t);
                Toast.makeText(MainActivity.this, "Error fetching recipes", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void logoutUser() {
        // Clear SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("YourPrefsName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();

        // now take me to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}

