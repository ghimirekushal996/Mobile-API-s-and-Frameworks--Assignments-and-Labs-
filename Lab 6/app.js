// app.js

const express = require("express");
const firebaseRoutes = require("./Routes/firebaseRoutes");

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());

// Include the routes
app.use("/auth", firebaseRoutes); // Firebase authentication routes

// Start the server
app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});