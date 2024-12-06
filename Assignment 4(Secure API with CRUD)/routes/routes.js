/**
 * File name: routes.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: November 15, 2024
 */

const express = require('express');
const Recipe = require('../models/models');
const authenticateToken = require('../middleware/auth');

const router = express.Router();

// Create a new recipe
router.post('/recipes', authenticateToken, async (req, res) => {
    try {
        const { recipeName, ingredients, cookingTime, difficulty, cuisine, description, photoLink, averageRating } = req.body;

        const newRecipe = new Recipe({
            recipeName,
            ingredients,
            cookingTime,
            difficulty,
            cuisine,
            description,
            photoLink,
            averageRating,
        });

        await newRecipe.save();
        res.status(201).json({ message: 'Recipe created successfully.', data: newRecipe });
    } catch (error) {
        res.status(500).json({ message: 'Error creating recipe.', error });
    }
});

// Get all recipes
router.get('/recipes', authenticateToken, async (req, res) => {
    try {
        const recipes = await Recipe.find();
        res.status(200).json(recipes);
    } catch (error) {
        res.status(500).json({ message: 'Error fetching recipes.', error });
    }
});



module.exports = router;

