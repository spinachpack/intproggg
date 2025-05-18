const express = require('express');
const router = express.Router();
const Reservation = require('../models/Reservation');

// Get all reservations
router.get('/', async (req, res) => {
  try {
    const reservations = await Reservation.find();
    res.json(reservations);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get reservations by user email
router.get('/user/:email', async (req, res) => {
  try {
    const reservations = await Reservation.find({ userEmail: req.params.email });
    res.json(reservations);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get upcoming reservations for a user
router.get('/user/:email/upcoming', async (req, res) => {
  try {
    const reservations = await Reservation.find({ 
      userEmail: req.params.email,
      status: 'RESERVED'
    }).sort({ date: 1, time: 1 });
    
    res.json(reservations);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get past reservations for a user
router.get('/user/:email/past', async (req, res) => {
  try {
    const reservations = await Reservation.find({ 
      userEmail: req.params.email,
      status: { $in: ['COMPLETED', 'CANCELLED'] }
    }).sort({ date: -1, time: -1 });
    
    res.json(reservations);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get a single reservation
router.get('/:id', async (req, res) => {
  try {
    const reservation = await Reservation.findOne({ id: req.params.id });
    if (!reservation) {
      return res.status(404).json({ message: 'Reservation not found' });
    }
    res.json(reservation);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Create a reservation
router.post('/', async (req, res) => {
  const reservation = new Reservation({
    id: req.body.id,
    restaurantId: req.body.restaurantId,
    restaurantName: req.body.restaurantName,
    userEmail: req.body.userEmail,
    date: req.body.date,
    time: req.body.time,
    guests: req.body.guests,
    status: req.body.status || 'RESERVED'
  });

  try {
    const newReservation = await reservation.save();
    res.status(201).json(newReservation);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Update reservation status
router.patch('/:id/status', async (req, res) => {
  try {
    const reservation = await Reservation.findOne({ id: req.params.id });
    if (!reservation) {
      return res.status(404).json({ message: 'Reservation not found' });
    }
    
    if (!req.body.status) {
      return res.status(400).json({ message: 'Status is required' });
    }

    reservation.status = req.body.status;
    
    const updatedReservation = await reservation.save();
    res.json(updatedReservation);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Update a reservation
router.put('/:id', async (req, res) => {
  try {
    const reservation = await Reservation.findOne({ id: req.params.id });
    if (!reservation) {
      return res.status(404).json({ message: 'Reservation not found' });
    }

    if (req.body.date) reservation.date = req.body.date;
    if (req.body.time) reservation.time = req.body.time;
    if (req.body.guests) reservation.guests = req.body.guests;
    if (req.body.status) reservation.status = req.body.status;

    const updatedReservation = await reservation.save();
    res.json(updatedReservation);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Delete a reservation
router.delete('/:id', async (req, res) => {
  try {
    const reservation = await Reservation.findOne({ id: req.params.id });
    if (!reservation) {
      return res.status(404).json({ message: 'Reservation not found' });
    }
    await Reservation.deleteOne({ id: req.params.id });
    res.json({ message: 'Reservation deleted' });
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

module.exports = router;