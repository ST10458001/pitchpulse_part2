# PitchPulse — Part 2 Prototype

**OPSC6312 POE — Group 10 — Tshegofatso Mokwele (ST10458001), Lindokuhle Nelson (ST10446928)**

PitchPulse is a live sports scores app that cuts the ad clutter and shallow stats found
in LiveScore, Sofascore, and SportyTV (see Part 1, Section A). This repo contains the
Part 2 prototype: authentication, settings, and a live scores feed backed by a
custom-built, hosted REST API.

## Repo structure

```
/api        Node.js + Express REST API (auth, settings, matches) — see api/README.md
/android    Android Kotlin app (Jetpack Compose)
PART2_PLAN.md   Scope decisions: what's built now vs. deferred to the final PoE
```

## What's implemented in this prototype

- Register / login with **bcrypt-hashed passwords**
- User **settings** (notification preferences), persisted via the API
- **Custom REST API**, hosted at: `<ADD YOUR RENDER/CLOUD RUN URL HERE>`
- Live/upcoming match feed pulled through the API from API-Football
- **GitHub Actions** CI for both the API (`api/.github/workflows/api-ci.yml`) and the
  Android build (`android/.github/workflows/android-ci.yml`)

## Deferred to the final PoE

Prediction League/gamification, server-rendered Stat Cards, push notifications,
offline mode, and the news Discover feed — all specified in Section B but not required
for Part 2. See `PART2_PLAN.md` for the full rationale.

## Demo video

`<ADD YOUR UNLISTED YOUTUBE LINK HERE>`

## AI Tool Usage Declaration

Claude (Anthropic) was used to help scaffold boilerplate (API routes, Retrofit
networking layer, Compose screens) and to structure this documentation. All generated
code was reviewed, tested, and adapted by the authors before inclusion. See Section B's
AI Tool Usage Declaration for the full policy followed across this project.
