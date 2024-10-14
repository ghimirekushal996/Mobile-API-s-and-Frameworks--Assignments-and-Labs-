/**
 * File name: routes.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: October 13, 2024
 */

const express = require('express');
const router = express.Router();
const recipeController = require('../controllers/recipeController');
const {validateRecipe}=require('../middleware/middleware');

//Route to get all recipe
router.get('/',recipeController.findAllRecipe);
//Route to get a recipe by id
router.get('/:id',recipeController.getRecipeById);
router.post('/create',validateRecipe,recipeController.create);
//Route to update a recipe by id
router.put('/update/:id', validateRecipe,recipeController.update);

module.exports = router; 