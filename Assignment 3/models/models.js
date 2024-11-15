const mongoose = require('mongoose');

// Define the schema for the data 
const recipeSchema = new mongoose.Schema({
    name: { type: String, required: true },
    ingredients: { type: [String], required: true },
    instructions: { type: String, required: true },
    dateAdded: { type: Date, default: Date.now },
});

// Create and export the model
const Recipe = mongoose.model('Recipe', recipeSchema);
module.exports = Recipe;
