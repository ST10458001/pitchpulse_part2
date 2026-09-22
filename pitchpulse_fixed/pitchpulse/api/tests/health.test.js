const request = require('supertest');

const express = require('express');
const app = express();
app.get('/', (req, res) => res.json({ status: 'ok' }));

describe('GET /', () => {
  it('returns status ok', async () => {
    const res = await request(app).get('/');
    expect(res.statusCode).toBe(200);
    expect(res.body.status).toBe('ok');
  });
});
