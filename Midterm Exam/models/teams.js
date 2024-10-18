/**
 * File name: teams.js
 * Student Name: Kushal Ghimire
 * StudentID: 200555588
 * Date: October 18, 2024
 */

const mongoose = require('mongoose');

// Define the schema for the teams collection
const teamSchema = new mongoose.Schema({
  teamId: { type: Number, required: true },
  teamName: { type: String, required: true },
  city: { type: String, required: true },
  founded: { type: String, required: true },
  coach: { type: String, required: true }
});


// Create and export the model
const Teams = mongoose.model('Teams', teamSchema);

module.exports = Teams;
