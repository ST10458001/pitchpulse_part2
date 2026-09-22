# PitchPulse - Sports Scores & Gamification App

> Tagline: Your Game. Your Stats. Your Way.  
> Module: OPSC6312 - Mobile Application Development (Part 2)  
> Authors: Tshegofatso Mokwele (ST10458001) & Lindokuhle Nelson (ST10446928)


## 📱 App Overview & Purpose

PitchPulse is a feature-rich Android sports application designed to deliver real-time match data, comprehensive statistical analysis, and interactive gamification without intrusive advertising.

### Core Value Proposition
 Ad-Light Experience: Eliminates disruptive full-screen interstitials, utilizing an optional Pulse Pro tier and minimal non-intrusive banners.
  AI Micro-Commentary (Pulse Mode): Generates contextual match sentences driven by real-time stat deltas.
  Stat Cards: Server-rendered lightweight SVG graphics providing high-contrast match insights (possession, xG, momentum).
  Prediction League (Gamification): Allows users to predict match outcomes, earn Pulse Points, unlock badges, and compete on community leaderboards.


## 📹 Video Presentation Showcase

Watch video on YouTube:
[![PitchPulse Presentation Showcase](https://youtu.be/GVMlWgndA64)
[Link:](https://youtu.be/GVMlWgndA64)

## 🎨 Design & Architecture

### System Architecture
The app connects to Firebase Cloud Services and custom REST endpoints for data handling, caching live fixture data locally via Room SQLite for seamless offline usage.

### Key Screen Layouts
Below are low-fidelity wireframes detailing the 12 core screen destinations:

| Home & Match Views | Gamification & Settings |
| :---: | :---: |
| Home Feed, Match Detail, Stats View | Prediction League, Achievements, Profile |

## 🛠️ Technology Stack & Dependencies

- Language: Kotlin
- UI Framework: Jetpack Compose & Material Design 3
- Database: Firebase Firestore (Remote) & Room SQLite (Local Cache)
- Authentication: Firebase Authentication (Email/Password, Google Sign-In, Anonymous Guest)
- Networking & Media: Retrofit / Ktor & Coil (Image & SVG Loading)
- Push Notifications: Firebase Cloud Messaging (FCM)
- CI/CD Pipeline: GitHub Actions (`.github/workflows/build.yml`)


## ⚙️ Build & Setup Instructions

[git clone](https://github.com/ST10458001/pitchpulse_part2/tree/main) 
  
