/**
 * File name: index.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: December 06, 2024
 */

const http = require('http');
const dotenv = require('dotenv');
const express = require('express');
const connectDB = require('./database/db');
const userRoutes = require('./routes/user');
const recipeRoutes = require('./routes/routes')

dotenv.config();

const port = process.env.API_PORT || 3000;
const app = express();

// Connect to MongoDB
connectDB();

// Middleware
app.use(express.json());

// Routes
app.use('/api/users', userRoutes);
app.use('/api/routes', recipeRoutes);

// Create the server
const server = http.createServer(app);

// Start the server
server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
