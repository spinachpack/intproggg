const express = require('express');
const router = express.Router();
const PreOrder = require('../models/PreOrder');

// Get all pre-orders
router.get('/', async (req, res) => {
  try {
    const preOrders = await PreOrder.find();
    res.json(preOrders);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get pre-order by reservation ID
router.get('/reservation/:reservationId', async (req, res) => {
  try {
    const preOrder = await PreOrder.findOne({ reservationId: req.params.reservationId });
    if (!preOrder) {
      return res.status(404).json({ message: 'Pre-order not found for this reservation' });
    }
    res.json(preOrder);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get a single pre-order
router.get('/:id', async (req, res) => {
  try {
    const preOrder = await PreOrder.findOne({ id: req.params.id });
    if (!preOrder) {
      return res.status(404).json({ message: 'Pre-order not found' });
    }
    res.json(preOrder);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Create a pre-order
router.post('/', async (req, res) => {
  // Check if a pre-order already exists for this reservation
  try {
    const existingPreOrder = await PreOrder.findOne({ reservationId: req.body.reservationId });
    
    if (existingPreOrder) {
      // If exists, update it
      existingPreOrder.items = req.body.items;
      const updatedPreOrder = await existingPreOrder.save();
      return res.json(updatedPreOrder);
    }
    
    // If doesn't exist, create new
    const preOrder = new PreOrder({
      id: req.body.id,
      reservationId: req.body.reservationId,
      items: req.body.items
    });

    const newPreOrder = await preOrder.save();
    res.status(201).json(newPreOrder);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Update a pre-order
router.put('/:id', async (req, res) => {
  try {
    const preOrder = await PreOrder.findOne({ id: req.params.id });
    if (!preOrder) {
      return res.status(404).json({ message: 'Pre-order not found' });
    }

    if (req.body.items) preOrder.items = req.body.items;

    const updatedPreOrder = await preOrder.save();
    res.json(updatedPreOrder);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Delete a pre-order
router.delete('/:id', async (req, res) => {
  try {
    const preOrder = await PreOrder.findOne({ id: req.params.id });
    if (!preOrder) {
      return res.status(404).json({ message: 'Pre-order not found' });
    }
    await PreOrder.deleteOne({ id: req.params.id });
    res.json({ message: 'Pre-order deleted' });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

module.exports = router;