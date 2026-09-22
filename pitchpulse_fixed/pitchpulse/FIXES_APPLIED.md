# What changed from the first scaffold

This copy has every fix we found while debugging the build tonight already applied,
so it should sync and build cleanly on first open. Specifically:

1. **compileSdk bumped 34 → 36** (android/app/build.gradle.kts) — your installed
   Android SDK / emulator needed 36.
2. **Kotlin bumped 1.9.24 → 2.0.21** (android/build.gradle.kts), plus the new
   `org.jetbrains.kotlin.plugin.compose` plugin added to both the root and app
   build.gradle.kts files — required because Kotlin 2.0+ moved the Compose
   compiler into its own plugin.
3. **Removed the old `composeOptions { kotlinCompilerExtensionVersion = ... }`
   block** from app/build.gradle.kts — this is now handled automatically by
   the compose plugin above, and having both causes a conflict.
4. **Added a launcher icon** — the original scaffold referenced
   `@mipmap/ic_launcher` but never included the actual icon files, causing
   "Android resource linking failed". This copy includes a simple vector-based
   adaptive icon (cyan circle on charcoal) under
   `android/app/src/main/res/mipmap-anydpi-v26` and `res/drawable`, so no
   Image Asset wizard step is needed. Swap it for your own artwork later if
   you like.
5. **Fixed a missing import** in `PitchPulseNavGraph.kt` — added
   `import androidx.compose.foundation.layout.padding`, which was causing
   "Unresolved reference 'padding'".

## To use this
1. Unzip and open the `android` folder in Android Studio (the one containing
   `settings.gradle.kts` directly inside it — same as before).
2. Let Gradle sync. It needs internet the first time to download Kotlin 2.0.21
   and the compose plugin.
3. If it asks about Gradle JDK, pick JDK 21 (or Android Studio's embedded JDK).
4. Build → Rebuild Project.

Everything else (README, PART2_PLAN, the API folder, deployment steps) is
unchanged from before.
