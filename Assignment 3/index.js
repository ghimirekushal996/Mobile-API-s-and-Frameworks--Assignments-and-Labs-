// index.js
const http = require('http');
const server = http.createServer(routes);
const { API_PORT} = process.env;
const routes = require('./routes/routes'); // Import routes

// Set up the port from the .env file
const port = process.env.PORT || API_PORT;

// Start the server
server.listen(port, () => {
    console.log(`Server is running on port ${port}`);
});
