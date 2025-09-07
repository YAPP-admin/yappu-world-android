# Repository Guidelines

## Project Structure & Module Organization
- Multi‑module Android project using Gradle (Kotlin DSL).
- Modules: `:app` (entry), `:core:{model,data-api,designsystem,ui,data,domain,common-android}`, `:feature:{home,notice,signup,login,schedule,profile,history,setting}`, `:detekt` (custom rules).
- Source: `module/src/main/java/...` and resources in `module/src/main/res`.
- Tests: unit in `module/src/test/...`, instrumented in `module/src/androidTest/...`.

## Build, Test, and Development Commands
- `./gradlew :app:assembleDebug` — build debug APK; `:app:bundleRelease` builds release AAB.
- `./gradlew :app:installDebug` — install on a connected device/emulator.
- `./gradlew test` — run unit tests across all modules.
- `./gradlew connectedDebugAndroidTest` — run instrumented tests (device/emulator required).
- `./gradlew detekt` — Kotlin static analysis (config: `detekt/src/main/resources/config/detekt-config.yml`).
- `./gradlew lint` — Android Lint; `./gradlew mergeDetektReports` merges reports to `build/reports/detekt/detekt.xml`.

## Coding Style & Naming Conventions
- Kotlin-first, 4‑space indentation, avoid wildcard imports; keep imports sorted.
- Naming: Classes/Objects `UpperCamelCase`; functions/vars `lowerCamelCase`; constants `UPPER_SNAKE_CASE`; packages lowercase.
- Compose/UI: one public component per file; filename matches class/composable.
- Detekt enforced in CI—fix issues or suppress with clear justification.

## Testing Guidelines
- Frameworks: JUnit for unit tests; AndroidX Test/Espresso for instrumented tests.
- Name tests with `*Test` suffix and mirror source packages (e.g., `core/data/src/test/...`).
- Add tests for new features and bug fixes; prefer unit tests for logic, use instrumented tests for Android APIs.

## Commit & Pull Request Guidelines
- Commit style: use prefixes like `feat`, `fix`, `refactor`, `chore` and reference issues (e.g., `feat/#205: add schedule chip`).
- Keep commits focused and descriptive. Include migration notes if configs/build change.
- PRs must include: summary, linked issues, screenshots for UI changes, and test notes. CI (build + detekt) must pass.

## Security & Configuration
- Do not commit secrets. Store signing data in `keystore.properties`; local configuration in `local.properties` (both ignored).
- Build types: `debug`, `qa`, `release`. Use `./gradlew :app:assembleQa` for QA builds.
- Use Gradle properties or GitHub Actions secrets for base URLs/keys—avoid hardcoding.

