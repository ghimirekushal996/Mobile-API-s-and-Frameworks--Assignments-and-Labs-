/**
 * File name: auth.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: November 15, 2024
 */


// middleware/auth.js
const jwt = require('jsonwebtoken');

// Middleware to verify JWT token
const authenticateToken = (req, res, next) => {
    const token = req.headers['authorization'];
    const tokenWithoutBearer = token.startsWith('Bearer ') ? token.slice(7) : token;
    if (!tokenWithoutBearer) {
        return res.status(401).json({ message: 'Access denied. No token provided.' });
    }

    try {
        const decoded = jwt.verify(tokenWithoutBearer, process.env.JWT_SECRET);
        req.user = decoded;
        next();
    } catch (error) {
        res.status(400).json({ message: 'Invalid token.' });
    }
};

module.exports = authenticateToken;
