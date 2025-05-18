const express = require('express');
const router = express.Router();
const User = require('../models/User');

// Get all users
router.get('/', async (req, res) => {
  try {
    const users = await User.find().select('-password');
    res.json(users);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Get a single user
router.get('/:email', async (req, res) => {
  try {
    const user = await User.findOne({ email: req.params.email }).select('-password');
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }
    res.json(user);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Register a new user
router.post('/register', async (req, res) => {
  // Check if user already exists
  try {
    const existingUser = await User.findOne({ email: req.body.email });
    if (existingUser) {
      return res.status(400).json({ message: 'User with this email already exists' });
    }

    const user = new User({
      firstName: req.body.firstName,
      lastName: req.body.lastName,
      email: req.body.email,
      phone: req.body.phone,
      password: req.body.password,
      profilePictureUrl: req.body.profilePictureUrl
    });

    const newUser = await user.save();
    
    // Don't send back the password
    const userResponse = {
      firstName: newUser.firstName,
      lastName: newUser.lastName,
      email: newUser.email,
      phone: newUser.phone,
      profilePictureUrl: newUser.profilePictureUrl
    };
    
    res.status(201).json(userResponse);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Login a user
router.post('/login', async (req, res) => {
  try {
    const user = await User.findOne({ email: req.body.email });
    if (!user) {
      return res.status(400).json({ message: 'Invalid email or password' });
    }

    // Simple password check (in a real app, you'd use bcrypt or similar)
    const validPassword = user.password === req.body.password;
    if (!validPassword) {
      return res.status(400).json({ message: 'Invalid email or password' });
    }

    // Don't send back the password
    const userResponse = {
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      phone: user.phone,
      profilePictureUrl: user.profilePictureUrl
    };

    res.json(userResponse);
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Update user details
router.put('/:email', async (req, res) => {
  try {
    const user = await User.findOne({ email: req.params.email });
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    if (req.body.firstName) user.firstName = req.body.firstName;
    if (req.body.lastName) user.lastName = req.body.lastName;
    if (req.body.phone) user.phone = req.body.phone;
    if (req.body.profilePictureUrl) user.profilePictureUrl = req.body.profilePictureUrl;
    
    const updatedUser = await user.save();
    
    // Don't send back the password
    const userResponse = {
      firstName: updatedUser.firstName,
      lastName: updatedUser.lastName,
      email: updatedUser.email,
      phone: updatedUser.phone,
      profilePictureUrl: updatedUser.profilePictureUrl
    };
    
    res.json(userResponse);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Update password
router.patch('/:email/password', async (req, res) => {
  try {
    const user = await User.findOne({ email: req.params.email });
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    // Verify current password (in a real app, you'd use bcrypt to compare)
    if (user.password !== req.body.currentPassword) {
      return res.status(400).json({ message: 'Current password is incorrect' });
    }

    user.password = req.body.newPassword;
    await user.save();
    
    res.json({ message: 'Password updated successfully' });
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

module.exports = router;