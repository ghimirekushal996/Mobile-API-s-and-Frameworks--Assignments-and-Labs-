package com.kushal.recipefinderapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kushal.recipefinderapp.R;
import com.kushal.recipefinderapp.model.Recipe;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> recipeList;

    public RecipeAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        // Bind data to the views
        holder.recipeName.setText(recipe.getRecipeName());
        holder.cuisine.setText(recipe.getCuisine());
        holder.difficulty.setText(String.valueOf(recipe.getAverageRating()));

        // Load the image using Picasso
        if (recipe.getPhotoLink() != null && !recipe.getPhotoLink().isEmpty()) {
            Picasso.get()
                    .load(recipe.getPhotoLink())
                    .placeholder(R.drawable.ic_launcher_background) // Add a placeholder image in your drawable folder
                    .error(R.drawable.ic_launcher_background) // Add an error image in your drawable folder
                    .into(holder.photo);
        } else {
            holder.photo.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList != null ? recipeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, cuisine, difficulty;
        ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tvRecipeName);
            cuisine = itemView.findViewById(R.id.tvCuisine);
            difficulty = itemView.findViewById(R.id.tvDifficulty);
            photo = itemView.findViewById(R.id.ivPhoto);
        }
    }
}

