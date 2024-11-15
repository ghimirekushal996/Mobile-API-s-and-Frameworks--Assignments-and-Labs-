// index.js
const http = require('http');
const dotenv = require('dotenv');
const { API_PORT} = process.env;
const routes = require('./routes/routes'); // Import routes
const connectDB = require('./database/db'); 

dotenv.config();

// Set up the port from the .env file
const port = process.env.API_PORT;


// Connect to MongoDB
connectDB();

// Create the server
const server = http.createServer(routes);

// Start the server
server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
