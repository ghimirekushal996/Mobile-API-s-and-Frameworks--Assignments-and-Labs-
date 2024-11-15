// Import Mongoose
const mongoose = require('mongoose');

const recipeSchema = new mongoose.Schema({
    recipeName: {
        type: String,
        required: true,
    },
    ingredients: {
        type: [String], // Array of strings for ingredients
        required: true,
    },
    cookingTime: {
        type: Number,
        required: true,
    },
    difficulty: {
        type: String,
        enum: ['Easy', 'Medium', 'Hard'], // Valid options for difficulty
        required: true,
    },
    cuisine: {
        type: String,
        required: true,
    },
    description: {
        type: String,
        required: true,
    },
    photoLink: {
        type: String,
        required: true,
    },
    averageRating: {
        type: Number,
        min: 0,
        max: 5,
        required: true,
    },
}, {
    timestamps: true, // Adds createdAt and updatedAt timestamps
});

// Create the Recipe model
const Recipe = mongoose.model('Recipe', recipeSchema);

// Export the model
module.exports = Recipe;
