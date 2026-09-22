# PitchPulse API

The REST API Gateway for PitchPulse. Sits between the Android client and Firestore /
API-Football, so the client never holds external API keys (see Section B, 6.1).

## Endpoints

| Method | Path | Auth | Purpose |
|---|---|---|---|
| POST | /v1/auth/register | — | Create an account, password hashed with bcrypt |
| POST | /v1/auth/login | — | Log in, returns a JWT |
| GET | /v1/settings | Bearer token | Get the current user's settings |
| PUT | /v1/settings | Bearer token | Update the current user's settings |
| GET | /v1/matches | — | Live/upcoming fixtures (60s cache) |

## Local setup

```bash
cd api
npm install
cp .env.example .env   # fill in JWT_SECRET, API_FOOTBALL_KEY
# add serviceAccountKey.json (Firebase Console > Project Settings > Service Accounts)
npm run dev
```

## Deploying (free hosting)

**Render.com** is the simplest option with no CLI required:
1. Push this repo to GitHub.
2. On Render, "New Web Service" → connect the repo → root directory `api`.
3. Build command: `npm install`. Start command: `npm start`.
4. Add environment variables from `.env.example` in the Render dashboard
   (paste your service account JSON into `FIREBASE_SERVICE_ACCOUNT_JSON`).
5. Deploy — Render gives you a public URL like `https://pitchpulse-api.onrender.com`.
6. Update `BASE_URL` in the Android app's `RetrofitClient.kt` to that URL.

## Testing

```bash
npm test
```

## CI

GitHub Actions (`.github/workflows/api-ci.yml`) installs dependencies and runs tests
on every push/PR that touches `api/`.
