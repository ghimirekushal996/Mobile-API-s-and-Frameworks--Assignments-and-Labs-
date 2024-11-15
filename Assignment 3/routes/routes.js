
const express = require('express');
const Recipe = require('../models/recipe');
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



module.exports = router;

