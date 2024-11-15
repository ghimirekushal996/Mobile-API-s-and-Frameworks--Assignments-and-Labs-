package com.kushal.recipefinderapp.model;


import com.google.gson.annotations.SerializedName;

public class Recipe {
    @SerializedName("recipeName")
    private String recipeName;

    @SerializedName("cuisine")
    private String cuisine;

    @SerializedName("description")
    private String description;

    @SerializedName("photoLink")
    private String photoLink;

    public String getRecipeName() {
        return recipeName;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoLink() {
        return photoLink;
    }
}
