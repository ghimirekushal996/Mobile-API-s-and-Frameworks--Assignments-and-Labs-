// firebaseRoutes.js

const express = require("express");
const router = express.Router();
const verifyToken = require("./firebaseController");

// Protected route (only accessible with valid token)
router.get("/protected", verifyToken, (req, res) => {
  res.status(200).json({ message: "Access granted to protected route", user: req.user });
});

module.exports = router;
