# Draft Plan: Prima MVP Implementation

## Goal
Implement a functional MVP (Minimal Layak Presentasi) Android prototype for Prima based on `prd.md` and `DESIGN.md`.

## Known Constraints & Rules
- MVP scope: Splash, Login, Dashboard, Warning, Progress, Profile.
- Design: Follow `DESIGN.md` (Soft Minimalist, "Digital Zen" style, Primary: Electric Teal #00E5BC, Typography: Plus Jakarta Sans, Cards: rounded corners, soft ambient shadows).
- Architecture: MVVM pattern in Android.
- Data: Dummy local data first. Fast API backend present in `@ml-models/` but for MVP, we rely mostly on basic function & navigation.
- Action items required by user:
  - Move the FastAPI backend from `@ml-models/` to a different directory.
  - Move the logos from `@.agents\assets\logo/` to a different directory (likely Android res folders).

## Technical Approach
- Move `.agents/assets/logo/*` into Android `res/drawable` and `res/mipmap` directories.
- Move `ml-models/` to a permanent directory (pending user choice).
- UI Framework: Pending user choice (XML/ViewBinding vs Jetpack Compose).

## Outstanding Questions
- New location for FastAPI backend.
- UI Framework choice.

