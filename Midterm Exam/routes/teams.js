/**
 * File name: teams.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: Octopber 18, 2024
 */

const express = require('express');
const router = express.Router();
const { findAllTeams, getTeamById } = require('../controllers/teamsController');



// Define the endpoint for fetching all teams
router.get('/', findAllTeams);
router.get('/:teamId', getTeamById)

module.exports = router;
