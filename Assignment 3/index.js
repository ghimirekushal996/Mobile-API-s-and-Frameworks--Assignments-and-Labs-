// index.js
const http = require('http');
const dotenv = require('dotenv');
const { API_PORT} = process.env;
const express = require('express');
const routes = require('./routes/routes'); // Import routes
const connectDB = require('./database/db'); 

const userRoutes = require('./routes/user'); // import user routes

dotenv.config();

// Set up the port from the .env file
const port = process.env.API_PORT;


const app = express();

// Connect to MongoDB
connectDB();

// Middleware
app.use(express.json());

// Routes
app.use('/api/users', userRoutes);

// Create the server
const server = http.createServer(routes);

// Start the server
server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
