/**
 * File name: teamsController.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: October 18, 2024
 */

const Teams = require('../models/teams');

// Controller to fetch all teams
exports.findAllTeams = async (req, res) => {
  try {
   

    const teams = await Teams.find(); // Fetch all teams from the MongoDB collection
    res.json(teams);
  } catch (error) {
    res.status(500).json({ message: 'Server error: Could not fetch teams' });
  }
};
