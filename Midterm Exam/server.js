/**
 * File name: server.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: October 18, 2024
 */

const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');
const path = require('path'); // Import path module

// Import routes for teams
const teamsRoutes = require('./routes/teams');

dotenv.config();

const app = express();
const port = process.env.PORT || 3000;

// Middleware
app.use(express.json());

// Serve static files from the 'views' directory
app.use(express.static(path.join(__dirname, 'views')));

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

// Handle 404 errors (Page Not Found)
app.use((req, res, next) => {
  res.status(404).json({ message: 'Page Not Found' });
});

// Error handling middleware for other errors
app.use((err, req, res, next) => {
  console.error(err.stack); // Log the error stack for debugging
  res.status(500).json({ message: 'Internal Server Error' });
});
