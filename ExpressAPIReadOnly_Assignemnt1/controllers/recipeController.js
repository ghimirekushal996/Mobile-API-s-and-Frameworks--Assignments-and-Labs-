/**
 * File name: recipeController.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: September 29, 2024
 */

const Recipe = require('../models/recipe');

// Controller to fetch all recipes
exports.findAllRecipes = async (req, res) => {
  try {
   

    const recipes = await Recipe.find(); // Fetch all recipes from the MongoDB collection
    res.json(recipes);
  } catch (error) {
    res.status(500).json({ message: 'Server error: Could not fetch recipes' });
  }
};
