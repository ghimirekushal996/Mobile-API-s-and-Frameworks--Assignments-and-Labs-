// routes/user.js
const express = require('express');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const User = require('../models/user');
const router = express.Router();

// Register API
router.post('/register', async (req, res) => {

    console.log("Registering..");

    const { username, email, password } = req.body;

    // Hash the password
    const hashedPassword = await bcrypt.hash(password, 10);

    // Create a new user
    const newUser = new User({
        username,
        email,
        password: hashedPassword,
    });

    try {
        await newUser.save();
        res.status(201).json({ message: 'User registered successfully.' });
    } catch (error) {
        res.status(500).json({ message: 'Error registering user.', error });
    }
});


router.get('/testing', async (req, res) => {

    console.log("testing..");

    res.status(201).json({ message: 'User registered successfully.' });
});

module.exports = router;
