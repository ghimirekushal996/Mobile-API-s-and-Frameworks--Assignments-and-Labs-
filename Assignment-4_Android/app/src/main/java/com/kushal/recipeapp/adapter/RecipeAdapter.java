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