// firebaseController.js

const admin = require("../models/firebaseModel");

const verifyToken = async (req, res, next) => {
  const authHeader = req.header("Authorization");

  if (!authHeader || !authHeader.startsWith("Bearer ")) {
    return res.status(401).json({ error: "Token is required" });
  }

  const token = authHeader.split("Bearer ")[1];

  try {
    const decodedToken = await admin.auth().verifyIdToken(token);
    req.user = decodedToken;
    next(); // Token is valid, move to the next route handler
  } catch (error) {
    res.status(401).json({ error: "Invalid or expired token" });
  }
};

module.exports = verifyToken;
