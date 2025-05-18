const mongoose = require('mongoose');

const reservationSchema = new mongoose.Schema({
  id: { type: String, required: true, unique: true },
  restaurantId: { type: String, required: true },
  restaurantName: { type: String, required: true },
  userEmail: { type: String, required: true },
  date: { type: String, required: true },
  time: { type: String, required: true },
  guests: { type: Number, required: true },
  status: { 
    type: String, 
    enum: ['RESERVED', 'COMPLETED', 'CANCELLED'],
    default: 'RESERVED'
  },
  createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Reservation', reservationSchema);