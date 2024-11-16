/**
 * File name: db.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: November 15, 2024
 */


const mongoose = require('mongoose');

// Function to connect to MongoDB
const connectDB = async () => {
    try {
        await mongoose.connect(process.env.MONGO_URI, {
            useNewUrlParser: true,
            useUnifiedTopology: true,
        });
        console.log('Connected to MongoDB');
    } catch (err) {
        console.error('Error connecting to MongoDB:', err);
        process.exit(1); // Exit the process if connection fails
    }
};

module.exports = connectDB;
