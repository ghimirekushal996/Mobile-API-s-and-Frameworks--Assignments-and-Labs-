/**
 * File name: recipe.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: September 29, 2024
 */

const express = require('express');
const router = express.Router();
const { findAllRecipes } = require('../controllers/recipeController');

// Define the endpoint for fetching all recipes
router.get('/', findAllRecipes);

module.exports = router;
