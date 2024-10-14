//Middleware to log incoming requests
const logger = (req,res,next)=>{
    console.log(`${req.method}${req.originalUrl} - ${new Date().toISOString()}`);
    next();
}

// Validate Recipe data before creating or updating
const validateRecipe = (req, res, next) => {
    const { recipeName, ingredients, cookingTime, difficulty, cuisine, description, photoLink, averageRating } = req.body;

    if (!recipeName || !ingredients || !cookingTime || !difficulty || !cuisine || !description || !photoLink || averageRating === undefined) {
        return res.status(400).send('Missing required fields: recipeName, ingredients, cookingTime, difficulty, cuisine, description, photoLink, or averageRating');
    }

    if (!Array.isArray(ingredients) || ingredients.length === 0) {
        return res.status(400).send('Invalid or missing ingredients. It should be a non-empty array.');
    }

    if (typeof cookingTime !== 'number' || cookingTime <= 0) {
        return res.status(400).send('Invalid cooking time. It should be a positive number representing minutes.');
    }

    if (typeof averageRating !== 'number' || averageRating < 0 || averageRating > 5) {
        return res.status(400).send('Invalid average rating. It should be a number between 0 and 5.');
    }

    if (typeof difficulty !== 'string' || !['Easy', 'Medium', 'Hard'].includes(difficulty)) {
        return res.status(400).send('Invalid difficulty. It should be either Easy, Medium, or Hard.');
    }

    next();
};


//Middleware to handle 404
const handleNotFound=(req,res)=>{
    res.status(404).send('Page not found');
};

module.exports ={
    logger,
    validateRecipe,
    handleNotFound
};