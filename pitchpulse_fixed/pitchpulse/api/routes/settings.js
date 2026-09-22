const express = require('express');
const { db } = require('../firebase');
const requireAuth = require('../middleware/requireAuth');

const router = express.Router();

const DEFAULT_SETTINGS = {
  theme: 'dark',
  notifyGoalsOnly: true,
  notifyAllEvents: false,
};

router.get('/', requireAuth, async (req, res) => {
  try {
    const ref = db.collection('users').doc(req.user.uid).collection('prefs').doc('settings');
    const doc = await ref.get();
    res.json(doc.exists ? doc.data() : DEFAULT_SETTINGS);
  } catch (err) {
    console.error('Get settings error:', err.message);
    res.status(500).json({ error: 'Could not load settings.' });
  }
});

router.put('/', requireAuth, async (req, res) => {
  try {
    const { theme, notifyGoalsOnly, notifyAllEvents } = req.body;
    const update = {};
    if (theme !== undefined) update.theme = theme;
    if (notifyGoalsOnly !== undefined) update.notifyGoalsOnly = !!notifyGoalsOnly;
    if (notifyAllEvents !== undefined) update.notifyAllEvents = !!notifyAllEvents;

    const ref = db.collection('users').doc(req.user.uid).collection('prefs').doc('settings');
    await ref.set(update, { merge: true });

    const doc = await ref.get();
    res.json(doc.data());
  } catch (err) {
    console.error('Update settings error:', err.message);
    res.status(500).json({ error: 'Could not update settings.' });
  }
});

module.exports = router;
