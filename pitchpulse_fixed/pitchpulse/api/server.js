require('dotenv').config();
const express = require('express');
const cors = require('cors');

const authRoutes = require('./routes/auth');
const matchesRoutes = require('./routes/matches');
const settingsRoutes = require('./routes/settings');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

app.use((req, res, next) => {
  console.log(`[${new Date().toISOString()}] ${req.method} ${req.path}`);
  next();
});

app.get('/', (req, res) => {
  res.json({ status: 'ok', service: 'PitchPulse API', version: 'v1' });
});

app.use('/v1/auth', authRoutes);
app.use('/v1/matches', matchesRoutes);
app.use('/v1/settings', settingsRoutes);

app.use((req, res) => {
  res.status(404).json({ error: 'Not found' });
});

app.listen(PORT, () => {
  console.log(`PitchPulse API listening on port ${PORT}`);
});

module.exports = app;
