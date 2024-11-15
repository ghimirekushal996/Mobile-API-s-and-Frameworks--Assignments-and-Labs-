const http = require('http');
const dotenv = require('dotenv');
const express = require('express');
const connectDB = require('./database/db');
const userRoutes = require('./routes/user');

dotenv.config();

const port = process.env.API_PORT || 3000;
const app = express();

// Connect to MongoDB
connectDB();

// Middleware
app.use(express.json());

// Routes
app.use('/api/users', userRoutes);

// Create the server
const server = http.createServer(app);

// Start the server
server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
