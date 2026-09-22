const express = require('express');
const axios = require('axios');

const router = express.Router();

const API_FOOTBALL_KEY = process.env.API_FOOTBALL_KEY;
const API_FOOTBALL_HOST = 'v3.football.api-sports.io';

let cache = { data: null, expiresAt: 0 };

router.get('/', async (req, res) => {
  try {
    const now = Date.now();
    if (cache.data && cache.expiresAt > now) {
      return res.json({ source: 'cache', matches: cache.data });
    }

    if (!API_FOOTBALL_KEY) {
      const stub = [
        { id: '1', home: 'Man Utd', away: 'Arsenal', score: '2-1', minute: 73, status: 'live' },
        { id: '2', home: 'Liverpool', away: 'Chelsea', score: '0-0', minute: null, status: 'upcoming' },
      ];
      return res.json({ source: 'stub', matches: stub });
    }

    const response = await axios.get(`https://${API_FOOTBALL_HOST}/fixtures`, {
      params: { live: 'all' },
      headers: { 'x-apisports-key': API_FOOTBALL_KEY },
    });

    const matches = (response.data.response || []).map(f => ({
      id: String(f.fixture.id),
      home: f.teams.home.name,
      away: f.teams.away.name,
      score: `${f.goals.home ?? 0}-${f.goals.away ?? 0}`,
      minute: f.fixture.status.elapsed,
      status: f.fixture.status.short,
    }));

    cache = { data: matches, expiresAt: now + 60_000 };
    res.json({ source: 'live', matches });
  } catch (err) {
    console.error('Matches fetch error:', err.message);
    res.status(502).json({ error: 'Could not fetch match data right now.' });
  }
});

module.exports = router;
