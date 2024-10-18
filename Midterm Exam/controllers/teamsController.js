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

// Function to get a single team by Id
exports.getTeamById = async (req, res) => {
  try {
      const team = await Teams.findById(req.params.teamId);
      if (!team) {
          return res.status(404).send('Team not found');
      }
      res.status(200).json(team);
  } catch (e) {
      console.error(e);
      res.status(500).send('Error retrieving the team');
  }
};
