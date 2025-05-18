const mongoose = require('mongoose');

const orderItemSchema = new mongoose.Schema({
  menuItem: {
    id: { type: String, required: true },
    restaurantId: { type: String, required: true },
    name: { type: String, required: true },
    description: { type: String },
    price: { type: Number, required: true },
    imageUrl: { type: String }
  },
  quantity: { type: Number, required: true, min: 1 }
});

const preOrderSchema = new mongoose.Schema({
  id: { type: String, required: true, unique: true },
  reservationId: { type: String, required: true, unique: true },
  items: [orderItemSchema],
  createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('PreOrder', preOrderSchema);