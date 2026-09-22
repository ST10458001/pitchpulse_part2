const express = require('express');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const { db } = require('../firebase');

const router = express.Router();
const JWT_SECRET = process.env.JWT_SECRET || 'dev-secret-change-me';
const SALT_ROUNDS = 10;

function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function isValidPassword(pw) {
  return typeof pw === 'string' && pw.length >= 8 && /[A-Z]/.test(pw) && /[a-z]/.test(pw) && /\d/.test(pw);
}

router.post('/register', async (req, res) => {
  try {
    const { email, password, displayName } = req.body;

    if (!email || !isValidEmail(email)) {
      return res.status(400).json({ error: 'A valid email is required.' });
    }
    if (!isValidPassword(password)) {
      return res.status(400).json({
        error: 'Password must be at least 8 characters and include an uppercase letter, a lowercase letter, and a number.'
      });
    }

    const usersRef = db.collection('users');
    const existing = await usersRef.where('email', '==', email).limit(1).get();
    if (!existing.empty) {
      return res.status(409).json({ error: 'An account with this email already exists.' });
    }

    const passwordHash = await bcrypt.hash(password, SALT_ROUNDS);

    const userDoc = await usersRef.add({
      email,
      passwordHash,
      displayName: displayName || email.split('@')[0],
      createdAt: new Date().toISOString(),
    });

    const token = jwt.sign({ uid: userDoc.id, email }, JWT_SECRET, { expiresIn: '7d' });

    console.log(`New user registered: ${userDoc.id}`);
    res.status(201).json({ uid: userDoc.id, email, token });
  } catch (err) {
    console.error('Register error:', err.message);
    res.status(500).json({ error: 'Registration failed. Please try again.' });
  }
});

router.post('/login', async (req, res) => {
  try {
    const { email, password } = req.body;
    if (!email || !password) {
      return res.status(400).json({ error: 'Email and password are required.' });
    }

    const usersRef = db.collection('users');
    const snapshot = await usersRef.where('email', '==', email).limit(1).get();
    if (snapshot.empty) {
      return res.status(401).json({ error: 'Invalid email or password.' });
    }

    const userDoc = snapshot.docs[0];
    const user = userDoc.data();
    const match = await bcrypt.compare(password, user.passwordHash);
    if (!match) {
      return res.status(401).json({ error: 'Invalid email or password.' });
    }

    const token = jwt.sign({ uid: userDoc.id, email }, JWT_SECRET, { expiresIn: '7d' });
    console.log(`User logged in: ${userDoc.id}`);
    res.json({ uid: userDoc.id, email, displayName: user.displayName, token });
  } catch (err) {
    console.error('Login error:', err.message);
    res.status(500).json({ error: 'Login failed. Please try again.' });
  }
});

module.exports = router;
