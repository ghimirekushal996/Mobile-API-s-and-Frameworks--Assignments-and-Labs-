/**
 * File name: recipe.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: September 29, 2024
 */

const mongoose = require('mongoose');

// Define the schema for the recipe collection
const recipeSchema = new mongoose.Schema({
  recipeName: { type: String, required: true },
  ingredients: { type: [String], required: true },
  cookingTime: { type: Number, required: true },
  difficulty: { type: String, required: true },
  cuisine: { type: String, required: true },
  description: { type: String, required: true },
  photoLink: { type: String, required: true },
  averageRating: { type: Number, required: true },
});

// Create and export the model
const Recipe = mongoose.model('Recipe', recipeSchema);

module.exports = Recipe;
