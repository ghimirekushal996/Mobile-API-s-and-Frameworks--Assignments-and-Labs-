package com.kushal.recipeapp.models;

import java.util.List;

public class AddRecipe {
    private String recipeName;
    private List<String> ingredients;
    private int cookingTime;
    private String difficulty;
    private String cuisine;
    private String description;
    private String photoLink;
    private float averageRating; // Add this field for average rating

    // Constructor
    public AddRecipe(String recipeName, List<String> ingredients, int cookingTime, String difficulty,
                     String cuisine, String description, String photoLink, float averageRating) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.cookingTime = cookingTime;
        this.difficulty = difficulty;
        this.cuisine = cuisine;
        this.description = description;
        this.photoLink = photoLink;
        this.averageRating = averageRating;
    }

    // Getters and setters
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }
}

