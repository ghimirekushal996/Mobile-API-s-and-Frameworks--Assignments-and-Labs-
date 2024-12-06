package com.kushal.recipeapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kushal.recipeapp.api.ApiService;
import com.kushal.recipeapp.models.Recipe;
import com.kushal.recipeapp.screen.AddUpdateRecipeActivity;
import com.kushal.recipeapp.sharedpreference.SharedPreferenceManager;
import com.squareup.picasso.Picasso;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private final Context context;
    private final List<Recipe> recipeList;

    ApiService apiService; // Make sure ApiService is initialized in your adapter

    public RecipeAdapter(Context context, List<Recipe> recipeList, ApiService apiService) {
        this.context = context;
        this.recipeList = recipeList;
        this.apiService = apiService;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each recipe
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        // Get the current recipe and bind the data to the views
        Recipe recipe = recipeList.get(position);
        holder.tvRecipeName.setText(recipe.getRecipeName());
        holder.tvCuisine.setText(recipe.getCuisine());
        holder.tvRating.setText(String.valueOf(recipe.getAverageRating()));

        // Use Picasso to load the image from the URL (recipe photo link)
        Picasso.get()
                .load(recipe.getPhotoLink())  // Assuming `getPhotoLink()` returns the image URL
                .into(holder.ivPhoto);

        // Set click listener for each item
        holder.itemView.setOnClickListener(v -> showEditDeleteDialog(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();  // Return the size of the recipe list
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeName, tvCuisine, tvRating;
        ImageView ivPhoto;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvCuisine = itemView.findViewById(R.id.tvCuisine);
            tvRating = itemView.findViewById(R.id.rbRating);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
        }
    }
    // Method to show the Edit/Delete dialog
    private void showEditDeleteDialog(int position) {
        final Recipe selectedRecipe = recipeList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose an Action");
        builder.setItems(new String[]{"Edit", "Delete"}, (dialog, which) -> {
            if (which == 0) {
                // Edit action: pass recipe details to AddUpdateRecipeActivity
                Intent intent = new Intent(context, AddUpdateRecipeActivity.class);
                intent.putExtra("_id", selectedRecipe.get_id());
                intent.putExtra("recipe_name", selectedRecipe.getRecipeName());
                intent.putExtra("cuisine", selectedRecipe.getCuisine());
                intent.putExtra("difficulty", selectedRecipe.getDifficulty());
                intent.putExtra("cooking_time", selectedRecipe.getCookingTime());
                intent.putExtra("description", selectedRecipe.getDescription());
                intent.putExtra("photo_link", selectedRecipe.getPhotoLink());
                intent.putExtra("ingredients", String.join(", ", selectedRecipe.getIngredients()));
                intent.putExtra("averageRating", String.valueOf(selectedRecipe.getAverageRating()));
                context.startActivity(intent);
            } else if (which == 1) {
                // Delete action
                showDeleteConfirmationDialog(position, selectedRecipe.get_id());
            }
        });
        builder.show();
    }

    // Method to show confirmation dialog for delete
    private void showDeleteConfirmationDialog(int position, String recipeId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure?");
        builder.setMessage("Do you really want to delete this recipe?");
        builder.setPositiveButton("Yes, Delete", (dialog, which) -> {
            // Perform deletion by calling the delete function
            deleteRecipe(position, recipeId);
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // Delete Recipe Method with API request
    private void deleteRecipe(int position, String recipeId) {
        // Get the token from shared preferences
        SharedPreferenceManager preferenceManager = new SharedPreferenceManager(context.getApplicationContext());
        String token = preferenceManager.getToken();

        // Call the API to delete the recipe
        apiService.deleteRecipe(token, recipeId)
                .enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Successfully deleted the recipe from the API
                            recipeList.remove(position);
                            notifyItemRemoved(position);
                        } else {
                            // Handle error in deleting (e.g., show a Toast or AlertDialog)
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                        // Handle failure (e.g., show a Toast or AlertDialog)
                    }
                });
    }
}
