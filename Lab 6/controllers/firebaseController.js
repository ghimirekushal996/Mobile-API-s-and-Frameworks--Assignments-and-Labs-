// controllers/firebaseController.js

const admin = require("../models/firebaseModel"); // Import Firebase Admin SDK instance
const axios = require("axios"); // For making HTTP requests

// Sign-up controller to create a new user
const createUser = async (req, res) => {
  const { email, password } = req.body;

  try {
    // Use Firebase Authentication to create a new user
    const userRecord = await admin.auth().createUser({
      email,
      password,
    });

    res.status(201).json({ message: "User created successfully", uid: userRecord.uid });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

// Sign-in controller to log in the user using email and password
const loginUser = async (req, res) => {
  const { email, password } = req.body;

  try {
    // Fetch user by email
    const user = await admin.auth().getUserByEmail(email);

    // Create a custom Firebase token
    const customToken = await admin.auth().createCustomToken(user.uid);
    
    // Call Firebase Auth API to sign in using the custom token
    const response = await axios.post(
      `https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=AIzaSyCHan2Siwz3O74SC7GxAuMxAOQghigeWmA`,
      {
        token: customToken,
        returnSecureToken: true, // Tells Firebase to return a secure ID token
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    const { idToken, refreshToken, expiresIn } = response.data;

    // Return the ID token, refresh token, and expiration time
    res.status(200).json({
      message: "Signed in successfully",
      idToken: idToken,
      refreshToken: refreshToken,
      expiresIn: expiresIn,
    });
  } catch (error) {
    res.status(400).json({ error: error.message });
  }
};

// Token verification middleware
const verifyToken = async (req, res, next) => {
  const authHeader = req.header("Authorization");

  if (!authHeader || !authHeader.startsWith("Bearer ")) {
    return res.status(401).json({ error: "No token provided" });
  }

  const token = authHeader.split("Bearer ")[1];

  try {
    const decodedToken = await admin.auth().verifyIdToken(token); // Verify Firebase ID token
    req.user = decodedToken; // Attach decoded token to request object
    next(); // Proceed to the next route handler
  } catch (error) {
    return res.status(401).json({ error: "Invalid token. Denied" });
  }
};

// Protected route handler (only accessible with valid token)
const protectedRoute = (req, res) => {
  res.status(200).json({
    message: `Welcome ${req.user.email}, you have access to this protected route.`,
  });
};

module.exports = { createUser, loginUser, verifyToken, protectedRoute };
