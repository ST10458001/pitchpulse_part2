# PitchPulse — Part 2 Plan (App Prototype Development)

Scope decision: build a **working slice** of the Section B design — enough to satisfy
every explicit Part 2 requirement — and defer the rest (Prediction League, Stat Cards,
offline mode, push notifications) to the final PoE, as the brief explicitly allows.

## What we are building now

| Feature | Why it's in scope | Maps to brief requirement |
|---|---|---|
| Register / Login (email+password, hashed) | Explicit brief requirement | "register and log in... encrypt the password" |
| Guest mode (optional, quick win) | Already in FR-001, cheap to add | Supports "invalid input handling" testing |
| Settings screen (notification toggles, theme) | Explicit brief requirement | "change their settings in the app" |
| Custom REST API (Node/Express) + Firestore, hosted on Render/Railway/Cloud Run | Explicit brief requirement | "connect to a REST API you create... hosted" |
| Home Feed pulling live scores via the API (API-Football passthrough) | One real, demoable feature | "your features specified in Part 1" |
| GitHub Actions CI (build + basic test) | Explicit brief requirement | CI links in brief |
| README with setup, purpose, GitHub/Actions explanation | Explicit brief requirement | Documentation section |
| Demo video (voice-over, shows app + DB + API data) | Explicit brief requirement | Submission section |

## Deferred to final PoE

- Prediction League / gamification / leaderboard
- Server-rendered SVG Stat Cards
- Push notifications (FCM)
- Offline mode / Room caching / 7-day TTL
- News feed (Discover tab)

## Build order (suggested, ~1–2 weeks)

1. **API first** (`/api` folder here): auth endpoints, matches passthrough endpoint,
   connect to Firestore, deploy to a free host (Render.com is simplest — no CLI setup).
2. **Android auth screens**: Register/Login UI (Compose) → Retrofit calls to your API →
   store JWT in encrypted storage.
3. **Settings screen**: a couple of toggles (persist to your API/Firestore or locally).
4. **Home Feed**: RecyclerView/LazyColumn hitting your `/matches` endpoint.
5. **GitHub Actions**: drop in the workflow (provided), push, confirm it goes green.
6. **README**: purpose, architecture diagram (reuse Section B's Figure 3), setup steps,
   Actions badge, link to demo video.
7. **Record video**: register → login → change a setting → view live scores → show
   Firestore console with the new user doc → show Postman/browser hitting your live API URL.
8. **If AI tools were used**: adapt your existing Section B "AI Tool Usage Declaration"
   into the ≤500-word write-up required for submission.

## Submission checklist

- [ ] App compiles and runs with no crashes on invalid input
- [ ] Password hashed, never logged in plaintext
- [ ] REST API is live at a public URL (not localhost) when you record the video
- [ ] GitHub Actions workflow passes (green check)
- [ ] README committed, includes video link
- [ ] Kotlin source pushed directly (no zip)
- [ ] Code commented + uses logging (Log.d/Log.i)
- [ ] Video: voice-over, shows register/login, settings, API data, DB data
- [ ] AI usage write-up included if applicable (≤500 words)
