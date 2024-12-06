/**
 * File name: user.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: December 06, 2024
 */

// models/user.js
const mongoose = require('mongoose');

// Define the schema for User
const userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true,
        unique: true,
    },
    email: {
        type: String,
        required: true,
        unique: true,
    },
    password: {
        type: String,
        required: true,
    },
}, {
    timestamps: true,
});

// Create the User model
const User = mongoose.model('User', userSchema);

module.exports = User;
