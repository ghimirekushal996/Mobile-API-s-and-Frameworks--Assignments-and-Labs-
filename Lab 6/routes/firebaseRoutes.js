// firebaseRoutes.js

const express = require("express");
const router = express.Router();

// Import controllers for signup, signin, and protected route
const { createUser, loginUser, verifyToken } = require("../controllers/firebaseController");

// Sign up route (create a new user)
router.post("/signup", createUser);

// Sign in route (login using email and password)
router.post("/signin", loginUser);

// Protected route (only accessible with valid token)
router.get("/protected", verifyToken, (req, res) => {
  res.status(200).json({ message: "Access granted to protected route", user: req.user });
});

module.exports = router;
