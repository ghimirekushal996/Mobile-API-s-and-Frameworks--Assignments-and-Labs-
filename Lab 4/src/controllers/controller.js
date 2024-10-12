const Movie = require('../models/Movies');
const fs = require('fs');

// Function to get all movies with optional search and filter by title, genre, and year
exports.getMovies = async (req, res) => {
    try {
        // Extract query parameters from the request
        const { title, genre, year } = req.query;

        // Build a query object based on the search and filter parameters
        let query = {};

        if (title) {
            // Use regular expressions for case-insensitive matching of movie titles
            query.title = { $regex: title, $options: 'i' };
        }
        
        if (genre) {
            // Exact match for genre
            query.genres = { $in: [genre] };
        }

        // Fetch movies from the database based on the query object
        const movies = await Movie.find(query);

        // Return the filtered list of movies
        res.status(200).json(movies);
    } catch (e) {
        console.error(e);
        res.status(500).send('Error retrieving movies');
    }
};

// Function to create a new movie
exports.createMovie = async (req, res) => {
    try {
        const newMovie = new Movie(req.body);
        await newMovie.save();
        res.status(201).json(newMovie);
    } catch (e) {
        console.error(e);
        res.status(500).send('Error creating movie');
    }
};

// Function to get a single movie by Id
exports.getMovieById = async (req, res) => {
    try {
        const movie = await Movie.findById(req.params.id);
        if (!movie) {
            return res.status(404).send('Movie not found');
        }
        res.status(200).json(movie);
    } catch (e) {
        console.error(e);
        res.status(500).send('Error retrieving the movie');
    }
};

// Function to update a movie by Id
exports.updateMovie = async (req, res) => {
    try {
        const updatedMovie = await Movie.findByIdAndUpdate(req.params.id, req.body, { new: true });
        if (!updatedMovie) {
            return res.status(404).send('Movie not updated');
        }
        res.status(200).json(updatedMovie);
    } catch (e) {
        console.error(e);
        res.status(500).send('Error updating the movie');
    }
};

// Function to delete a single movie by Id
exports.deleteMovie = async (req, res) => {
    try {
        const deletedMovie = await Movie.findByIdAndDelete(req.params.id);
        if (!deletedMovie) {
            return res.status(404).send('Movie not found');
        }
        res.status(200).json(deletedMovie);
    } catch (e) {
        console.error(e);
        res.status(500).send('Error deleting the movie');
    }
};

// Function to import movies (moved from index.js)
exports.importMovies = async (req, res) => {
    try {
        const data = JSON.parse(fs.readFileSync('./movies.json', 'utf-8')); // Read data from movies.json
        const count = await Movie.countDocuments();
        if (count === 0) {
            await Movie.create(data); // Create movies in the database
            console.log('Data successfully imported to MongoDb');
            res.status(200).send('Data successfully imported');
        } else {
            res.status(200).send('Data already exists, skipping import');
        }
    } catch (e) {
        console.error('Error importing data', e);
        res.status(500).send('Error importing data');
    }
};
