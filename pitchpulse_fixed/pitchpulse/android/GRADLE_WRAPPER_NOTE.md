# Gradle Wrapper

The binary wrapper files (`gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar`)
are not included here since they're binaries. Android Studio generates them
automatically the first time you open this `android` folder as a project.

If they're ever missing and Android Studio doesn't offer to create them, run:
    gradle wrapper --gradle-version 8.7
from a terminal inside this `android` folder (requires Gradle installed globally).
