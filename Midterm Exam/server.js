/**
 * File name: server.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: October 18, 2024
 */

const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');

// import routes of a teams
const recipeRoutes = require('./routes/teams');

dotenv.config();

const app = express();
const port = process.env.PORT || 3000;

// Middleware
app.use(express.json());

// Routes
app.use('/api/teams', teamsRoutes);

// Connect to MongoDB
mongoose.connect(process.env.MONGODB_URI)
  .then(() => {
    console.log('Connected to MongoDB');
    app.listen(port, () => {
      console.log(`Server running on port ${port}`);
    });
  })
  .catch((error) => {
    console.log('Error connecting to MongoDB:', error);
  });

