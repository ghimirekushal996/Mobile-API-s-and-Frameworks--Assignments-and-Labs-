/**
 * File name: teams.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: Octopber 18, 2024
 */

const express = require('express');
const router = express.Router();
const { findAllTeams } = require('../controllers/teamsController');



// Define the endpoint for fetching all teams
router.get('/', findAllTeams);

module.exports = router;
