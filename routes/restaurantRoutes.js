const express = require('express');
const router = express.Router();
const Restaurant = require('../models/Restaurant');

// Get all restaurants
router.get('/', async (req, res) => {
  try {
    const restaurants = await Restaurant.find();
    res.json(restaurants);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get single restaurant
router.get('/:id', async (req, res) => {
  try {
    const restaurant = await Restaurant.findOne({ id: req.params.id });
    if (!restaurant) {
      return res.status(404).json({ message: 'Restaurant not found' });
    }
    res.json(restaurant);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Create restaurant
router.post('/', async (req, res) => {
  const restaurant = new Restaurant({
    id: req.body.id,
    name: req.body.name,
    address: req.body.address,
    city: req.body.city,
    zipCode: req.body.zipCode,
    country: req.body.country,
    openingHours: req.body.openingHours,
    closingHours: req.body.closingHours,
    imageUrl: req.body.imageUrl,
    description: req.body.description
  });

  try {
    const newRestaurant = await restaurant.save();
    res.status(201).json(newRestaurant);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Update restaurant
router.put('/:id', async (req, res) => {
  try {
    const restaurant = await Restaurant.findOne({ id: req.params.id });
    if (!restaurant) {
      return res.status(404).json({ message: 'Restaurant not found' });
    }

    if (req.body.name) restaurant.name = req.body.name;
    if (req.body.address) restaurant.address = req.body.address;
    if (req.body.city) restaurant.city = req.body.city;
    if (req.body.zipCode) restaurant.zipCode = req.body.zipCode;
    if (req.body.country) restaurant.country = req.body.country;
    if (req.body.openingHours) restaurant.openingHours = req.body.openingHours;
    if (req.body.closingHours) restaurant.closingHours = req.body.closingHours;
    if (req.body.imageUrl) restaurant.imageUrl = req.body.imageUrl;
    if (req.body.description) restaurant.description = req.body.description;

    const updatedRestaurant = await restaurant.save();
    res.json(updatedRestaurant);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Delete restaurant
router.delete('/:id', async (req, res) => {
  try {
    const restaurant = await Restaurant.findOne({ id: req.params.id });
    if (!restaurant) {
      return res.status(404).json({ message: 'Restaurant not found' });
    }
    await Restaurant.deleteOne({ id: req.params.id });
    res.json({ message: 'Restaurant deleted' });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

module.exports = router;