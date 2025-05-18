const express = require('express');
const router = express.Router();
const MenuItem = require('../models/MenuItem');

// Get all menu items
router.get('/', async (req, res) => {
  try {
    const menuItems = await MenuItem.find();
    res.json(menuItems);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get menu items for a specific restaurant
router.get('/restaurant/:restaurantId', async (req, res) => {
  try {
    const menuItems = await MenuItem.find({ restaurantId: req.params.restaurantId });
    res.json(menuItems);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get a single menu item
router.get('/:id', async (req, res) => {
  try {
    const menuItem = await MenuItem.findOne({ id: req.params.id });
    if (!menuItem) {
      return res.status(404).json({ message: 'Menu item not found' });
    }
    res.json(menuItem);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Create a menu item
router.post('/', async (req, res) => {
  const menuItem = new MenuItem({
    id: req.body.id,
    restaurantId: req.body.restaurantId,
    name: req.body.name,
    description: req.body.description,
    price: req.body.price,
    imageUrl: req.body.imageUrl
  });

  try {
    const newMenuItem = await menuItem.save();
    res.status(201).json(newMenuItem);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Update a menu item
router.put('/:id', async (req, res) => {
  try {
    const menuItem = await MenuItem.findOne({ id: req.params.id });
    if (!menuItem) {
      return res.status(404).json({ message: 'Menu item not found' });
    }

    if (req.body.name) menuItem.name = req.body.name;
    if (req.body.description) menuItem.description = req.body.description;
    if (req.body.price) menuItem.price = req.body.price;
    if (req.body.imageUrl) menuItem.imageUrl = req.body.imageUrl;

    const updatedMenuItem = await menuItem.save();
    res.json(updatedMenuItem);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Delete a menu item
router.delete('/:id', async (req, res) => {
  try {
    const menuItem = await MenuItem.findOne({ id: req.params.id });
    if (!menuItem) {
      return res.status(404).json({ message: 'Menu item not found' });
    }
    await MenuItem.deleteOne({ id: req.params.id });
    res.json({ message: 'Menu item deleted' });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

module.exports = router;