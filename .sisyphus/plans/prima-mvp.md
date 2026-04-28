# Work Plan: Prima Android MVP Implementation

## 1. Core Objective
Develop a functional Minimal Layak Presentasi (MVP) prototype for the Prima Android App using Jetpack Compose, while adhering to the existing MVVM architecture and the "Digital Zen" soft minimalist design system. The prototype relies exclusively on dummy JSON data for demonstration purposes. 

Additionally, perform project restructuring to preserve the FastAPI backend (`ml-models/` -> `backend/`) and logo assets (`.agents/assets/logo/` -> Android `res/`) before they are deleted in production.

## 2. Scope Boundaries

**IN SCOPE (Must Do):**
- Restructure project directories (move `ml-models/` to `backend/`, logos to Android `res/`).
- Enable Jetpack Compose in the existing Android build configuration safely.
- Implement the "Digital Zen" Design System in Compose (Theme, Typography, Colors).
- Implement 6 MVP screens: Splash, Login, Dashboard, Warning, Progress, Profile.
- Implement Bottom Navigation for Home, Progress, Warning, Profile.
- Create a dummy data repository returning hardcoded models to power the UI.
- Use the existing `MainViewModel` or create new ViewModels per feature following MVVM.

**OUT OF SCOPE (Must NOT Do):**
- Real backend API integration, HTTP calls, or networking.
- On-device ML inference.
- Real authentication (any username/password should allow login).
- Rewriting the entire existing MVVM architecture or introducing new DI frameworks (e.g., Hilt/Koin).
- Persistent local storage (Room/DataStore) - in-memory data is sufficient.
- Connected UI tests on emulators (unless required by the user manually later).

## 3. Technical Approach
- **UI Framework:** Jetpack Compose (Material 3).
- **Architecture:** MVVM using `ViewModel` and Kotlin `StateFlow`.
- **Navigation:** Compose Navigation with a central `NavHost`.
- **Styling:** Custom Compose `MaterialTheme` mapping to "Digital Zen" specifications.
- **Verification:** Strict verification using Gradle CLI commands (`assembleDebug`, `lintDebug`, `testDebugUnitTest`) to ensure Compose integration does not break the build.

## 4. Implementation Tasks

### 4.1 Project Directory Restructuring
- **Goal:** Move FastAPI backend and Logos out of future-deleted folders.
- **Instructions:**
  1. Create a `backend/` directory at the project root.
  2. Move all contents of `ml-models/` to `backend/`.
  3. Move `.agents/assets/logo/*` (all variations: round, square, transparent) to `app/src/main/res/drawable/` as PNGs or `res/mipmap-xxhdpi/` appropriately.
  4. Ensure `app/src/main/res/drawable/ic_launcher_foreground.xml` or custom assets refer to Prima branding correctly.
- **Verification:**
  1. Verify `backend/main.py` exists.
  2. Verify `app/src/main/res/drawable/logo.png` (or similar) exists.

### 4.2 Enable Jetpack Compose in Android Project
- **Goal:** Safely integrate Compose into the `app` module.
- **Instructions:**
  1. Modify `app/build.gradle.kts`: Add `id("org.jetbrains.kotlin.plugin.compose")` or appropriate Compose compiler plugin. Enable `compose = true` in the `buildFeatures` block.
  2. Add dependencies from `libs.versions.toml` (`androidx-activity-compose`, `androidx-compose-ui`, `androidx-compose-material3`, etc.) to the `dependencies` block of `app/build.gradle.kts`.
  3. Ensure Kotlin version matches the Compose compiler extension version. (Check root `build.gradle.kts` and `libs.versions.toml`).
- **Verification:**
  1. Run `.\gradlew :app:assembleDebug` and verify it builds successfully.

### 4.3 Setup "Digital Zen" Design System in Compose
- **Goal:** Create `Theme.kt`, `Color.kt`, and `Type.kt` reflecting `DESIGN.md`.
- **Instructions:**
  1. Create package `com.fik.upnvj.ews.ui.theme`.
  2. Define Colors: `Electric Teal` (`#00E5BC`), `Ghost Gray` (`#F8FAFB`), `White` (`#FFFFFF`), `Deep Charcoal` (`#121212`), `Dark Teal` (`#006B57`). Ensure both Light and Dark mode maps are implemented per `DESIGN.md`.
  3. Define Typography: Map `Plus Jakarta Sans` to Compose `Typography` levels (Display, H1, H2, H3, Body-Lg/Md/Sm, Label-Bold/Md).
  4. Define Shapes: Use `RoundedCornerShape` with 8px (Small), 16px (Medium), 24px (Large) as per the "Rounded (Level 2)" shapes.
  5. Implement Custom `MaterialTheme`.
- **Verification:**
  1. `Theme.kt` is successfully created and compilable.

### 4.4 Implement Core UI Components (Reusable)
- **Goal:** Build the components specified in `prd.md` for reuse.
- **Instructions:**
  1. Create package `com.fik.upnvj.ews.ui.components`.
  2. Create `PrimaryButton`: Pill-shaped, Electric Teal bg, Charcoal text, large corner radius.
  3. Create `StatCard` and `ProgressCard`: White background, 16px corner radius, soft ambient shadow.
  4. Create `PrimaTopBar` and `PrimaBottomNav` for Scaffold integration.
  5. Create `RiskBadge`: Small pill shape showing warning severity colors.
- **Verification:**
  1. Build succeeds. Use Compose Previews to verify components match styles visually.

### 4.5 Dummy Data Layer (Repository & Models)
- **Goal:** Provide mock data to ViewModels.
- **Instructions:**
  1. Create `DummyDataRepository` under `com.fik.upnvj.ews.data.repository`.
  2. Create simple Kotlin object holding lists of dummy Warnings, Progress (IPS history), Profile Info (e.g., "Rafael", "2410512082", "IPK 3.25"), and Dashboard summary.
  3. Update `MainViewModel` or create `DashboardViewModel` to expose this dummy data via `StateFlow<UiState>`.
- **Verification:**
  1. Write a simple Unit Test (`testDebugUnitTest`) verifying the Dummy Repository returns the expected parsed default values.

### 4.6 Implement MVP Screens (Compose)
- **Goal:** Build the 6 primary screens specified in `prd.md`.
- **Instructions:**
  1. **Splash:** Logo centered, Prima name, simulated delay (e.g., `LaunchedEffect(Unit) { delay(1500) }`).
  2. **Login:** Simple dummy auth screen. Any `NIM/Password` -> Dashboard. Use `Ghost Gray` input fields with 12px corners.
  3. **Dashboard:** Greeting, GPA/SKS summary, Risk Level Card, Quick Actions.
  4. **Warning:** Graduation probability, Risk Label, Reasons list, Recommendation card.
  5. **Progress:** SKS progress bar, simple custom Chart (e.g., Bar chart) for IPS per semester.
  6. **Profile:** Name, NIM, Faculty, Logout button.
- **Verification:**
  1. `assembleDebug` passes. Compose Previews for each screen render successfully without data.

### 4.7 Navigation & Integration
- **Goal:** Connect screens into a single `NavHost` flow inside `MainActivity`.
- **Instructions:**
  1. Define `NavHost` with routes: `splash`, `login`, `dashboard`, `warning`, `progress`, `profile`.
  2. Set `MainActivity.kt` `setContent { PrimaTheme { AppNavigation() } }`.
  3. Wire the `PrimaBottomNav` to navigate between the 4 main tabs seamlessly using standard Compose Navigation state preserving flags (e.g., `popUpTo`, `launchSingleTop`).
- **Verification:**
  1. App builds and launches successfully on emulator.
  2. User can navigate from Splash -> Login -> Dashboard -> Profile -> Logout -> Login.

[DECISION NEEDED] - Check with user on precise Compose compiler version matching their Kotlin version.

## Final Verification Wave

- [ ] Run `.\gradlew :app:assembleDebug` - Must succeed without Compose compiler errors.
- [ ] Run `.\gradlew :app:lintDebug` - Must succeed.
- [ ] Run `.\gradlew :app:testDebugUnitTest` - Must succeed.
- [ ] Verify app launches Splash -> Login -> Dashboard.
- [ ] Verify Bottom Navigation cycles between Dashboard, Warning, Progress, and Profile.
- [ ] Verify dummy data loads and displays correctly on all screens.
