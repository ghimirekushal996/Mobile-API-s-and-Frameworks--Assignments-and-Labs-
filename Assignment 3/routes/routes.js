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

// Get a single recipe by ID
router.get('/recipes/:id', authenticateToken, async (req, res) => {
    try {
        const recipe = await Recipe.findById(req.params.id);
        if (!recipe) {
            return res.status(404).json({ message: 'Recipe not found.' });
        }
        res.status(200).json(recipe);
    } catch (error) {
        res.status(500).json({ message: 'Error fetching recipe.', error });
    }
});

// Update a recipe by ID
router.put('/recipes/:id', authenticateToken, async (req, res) => {
    try {
        const updatedRecipe = await Recipe.findByIdAndUpdate(req.params.id, req.body, {
            new: true,
            runValidators: true,
        });

        if (!updatedRecipe) {
            return res.status(404).json({ message: 'Recipe not found.' });
        }

        res.status(200).json({ message: 'Recipe updated successfully.', data: updatedRecipe });
    } catch (error) {
        res.status(500).json({ message: 'Error updating recipe.', error });
    }
});

// Delete a recipe by ID
router.delete('/recipes/:id', authenticateToken, async (req, res) => {
    try {
        const deletedRecipe = await Recipe.findByIdAndDelete(req.params.id);

        if (!deletedRecipe) {
            return res.status(404).json({ message: 'Recipe not found.' });
        }

        res.status(200).json({ message: 'Recipe deleted successfully.' });
    } catch (error) {
        res.status(500).json({ message: 'Error deleting recipe.', error });
    }
});

module.exports = router;

