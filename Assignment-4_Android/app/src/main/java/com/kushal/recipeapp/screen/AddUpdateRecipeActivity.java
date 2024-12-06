package com.kushal.recipeapp.screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kushal.recipeapp.R;
import com.kushal.recipeapp.network_config.ApiClient;
import com.kushal.recipeapp.network_config.ApiService;
import com.kushal.recipeapp.models.CreateRecipeModel;
import com.kushal.recipeapp.sharedpreference.SharedPreferenceManager;

import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUpdateRecipeActivity extends AppCompatActivity {
    private EditText etRecipeName, etCuisine, etCookingTime, etDescription, etIngredients, etPhotoLink, etRating;
    private AutoCompleteTextView etDifficulty;  // Use AutoCompleteTextView for difficulty
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_recipe);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar); // Assuming you have the Toolbar in XML layout
        setSupportActionBar(toolbar);

        // Enable the back button in the app bar
        ActionBar actionBar = getSupportActionBar();


        // Initialize views
        etRecipeName = findViewById(R.id.etRecipeName);
        etCuisine = findViewById(R.id.etCuisine);
        etDifficulty = findViewById(R.id.etDifficulty);  // Reference to AutoCompleteTextView
        etCookingTime = findViewById(R.id.etCookingTime);
        etDescription = findViewById(R.id.etDescription);
        etIngredients = findViewById(R.id.etIngredients);
        etPhotoLink = findViewById(R.id.etPhotoLink);
        etRating = findViewById(R.id.etAverageRating);
        btnSave = findViewById(R.id.btnSaveRecipe);

        // Set up difficulty options in AutoCompleteTextView
        String[] difficultyOptions = {"Easy", "Medium", "Hard"};
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, difficultyOptions);
        etDifficulty.setAdapter(difficultyAdapter);

        // Get the recipe data from the Intent
        Intent intent = getIntent();
        String _id = intent.getStringExtra("_id");
        String recipeName = intent.getStringExtra("recipe_name");
        String cuisine = intent.getStringExtra("cuisine");
        String difficulty = intent.getStringExtra("difficulty");
        int cookingTime = intent.getIntExtra("cooking_time", 0);
        String description = intent.getStringExtra("description");
        String photoLink = intent.getStringExtra("photo_link");
        String ingredients = intent.getStringExtra("ingredients");
        String rating = intent.getStringExtra("averageRating");

        if (recipeName == null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  // Show back button
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setTitle("Add Recipe");  // Default title for adding
        } else {
            actionBar.setDisplayHomeAsUpEnabled(true);  // Show back button
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setTitle("Edit Recipe");  // Default title for adding
        }

        if (recipeName == null) {
            btnSave.setText("Add Recipe");
        } else {
            btnSave.setText("Edit Recipe");
        }

        // Set the values to the EditText fields
        etRecipeName.setText(recipeName);
        etCuisine.setText(cuisine);
        etDifficulty.setText(difficulty);
        etCookingTime.setText(String.valueOf(cookingTime));
        etDescription.setText(description);
        etPhotoLink.setText(photoLink);
        etIngredients.setText(ingredients);
        etRating.setText(rating);

        // Handle Save and Cancel button actions
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipeName == null) {
                    saveRecipe();
                } else {
                    // perform update operation
                    updateRecipe(_id);
                }
            }
        });
    }

    private void saveRecipe() {
        // Get the data from EditText fields
        String recipeName = etRecipeName.getText().toString();
        String cuisine = etCuisine.getText().toString();
        String difficulty = etDifficulty.getText().toString();
        int cookingTime;
        try {
            cookingTime = Integer.parseInt(etCookingTime.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid cooking time", Toast.LENGTH_SHORT).show();
            return;
        }
        String description = etDescription.getText().toString();
        String photoLink = etPhotoLink.getText().toString();
        String ingredientsText = etIngredients.getText().toString(); // Get ingredients text
        String averageRatinging = etRating.getText().toString();

        // Split ingredients into a list
        String[] ingredientsArray = ingredientsText.split(",");
        List<String> ingredientsList = Arrays.asList(ingredientsArray);

        // Validate inputs
        if (recipeName.isEmpty() || cuisine.isEmpty() || difficulty.isEmpty() || description.isEmpty() || ingredientsText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the Recipe object to send to the API
        CreateRecipeModel addRecipe = new CreateRecipeModel(recipeName, ingredientsList, cookingTime, difficulty, cuisine, description, photoLink, Float.valueOf(averageRatinging.toString()));

        // Get the authorization token from SharedPreferences
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(this);
        String token = sharedPreferenceManager.getToken();

        // Make the API call to create the recipe
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<CreateRecipeModel> call = apiService.createRecipe("Bearer " + token, addRecipe);

        // Execute the API call asynchronously
        call.enqueue(new Callback<CreateRecipeModel>() {
            @Override
            public void onResponse(Call<CreateRecipeModel> call, Response<CreateRecipeModel> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(AddUpdateRecipeActivity.this, "Recipe saved successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddUpdateRecipeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(AddUpdateRecipeActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateRecipeModel> call, Throwable t) {
                // Handle failure (e.g., network error)
                Toast.makeText(AddUpdateRecipeActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecipe(String recipeId) {
        // Get the data from EditText fields
        String recipeName = etRecipeName.getText().toString();
        String cuisine = etCuisine.getText().toString();
        String difficulty = etDifficulty.getText().toString();
        int cookingTime;
        try {
            cookingTime = Integer.parseInt(etCookingTime.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid cooking time", Toast.LENGTH_SHORT).show();
            return;
        }
        String description = etDescription.getText().toString();
        String photoLink = etPhotoLink.getText().toString();
        String ingredientsText = etIngredients.getText().toString(); // Get ingredients text
        String averageRatinging = etRating.getText().toString();

        // Split ingredients into a list
        String[] ingredientsArray = ingredientsText.split(",");
        List<String> ingredientsList = Arrays.asList(ingredientsArray);

        // Validate inputs
        if (recipeName.isEmpty() || cuisine.isEmpty() || difficulty.isEmpty() || description.isEmpty() || ingredientsText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the Recipe object to send to the API for updating
        CreateRecipeModel updateRecipe = new CreateRecipeModel(recipeName, ingredientsList, cookingTime, difficulty, cuisine, description, photoLink, Float.valueOf(averageRatinging));

        // Get the authorization token from SharedPreferences
        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(this);
        String token = sharedPreferenceManager.getToken();

        // Make the API call to update the recipe
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Call<CreateRecipeModel> call = apiService.updateRecipe("Bearer " + token, recipeId, updateRecipe); // PUT request

        // Execute the API call asynchronously
        call.enqueue(new Callback<CreateRecipeModel>() {
            @Override
            public void onResponse(Call<CreateRecipeModel> call, Response<CreateRecipeModel> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(AddUpdateRecipeActivity.this, "Recipe updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddUpdateRecipeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(AddUpdateRecipeActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateRecipeModel> call, Throwable t) {
                // Handle failure (e.g., network error)
                Toast.makeText(AddUpdateRecipeActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the back button click
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

