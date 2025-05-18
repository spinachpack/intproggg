const mongoose = require('mongoose');

const restaurantSchema = new mongoose.Schema({
  id: { type: String, required: true, unique: true },
  name: { type: String, required: true },
  address: { type: String, required: true },
  city: { type: String, required: true },
  zipCode: { type: String, required: true },
  country: { type: String, required: true },
  openingHours: { type: String, required: true },
  closingHours: { type: String, required: true },
  imageUrl: { type: String },
  description: { type: String }
});

module.exports = mongoose.model('Restaurant', restaurantSchema);