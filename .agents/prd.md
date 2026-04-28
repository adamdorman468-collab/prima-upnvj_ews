# Product Requirements Document (PRD)

## Project: Prima – Early Warning System Mobile App (Prototype)

---

## 1. Product Overview

### Product Name
**Prima**

### Product Type
Android mobile application prototype for academic early warning system.

### Current Status
UI/UX design stage. Functional backend not required yet.

### Product Vision
Prima helps students monitor academic progress early, understand risks, and improve their chance of graduating on time.

---

## 2. Problem Statement

Many students realize academic issues too late, such as:
- GPA/IPS decline
- Credit units (SKS) below target
- Late graduation risk
- Lack of academic consultation
- Scattered academic information

Prima solves this through a simple mobile experience.

---

## 3. Target Users

### Primary Users
- University students

### Secondary Users (Future)
- Academic advisors / Dosen Wali
- Program heads / Kaprodi

---

## 4. MVP Scope (Minimal Layak Presentasi)

### Included
- Splash screen
- Login screen
- Dashboard
- Risk / Warning page
- Academic Progress page
- Profile page
- Navigation flow prototype

### Excluded (Future)
- Real authentication
n- Backend API
- Database integration
- Live machine learning prediction
- Push notifications
- Chat / consultation system

---

## 5. Core Value Proposition

Students can understand their academic condition in seconds through a clean and informative mobile app.

---

## 6. User Flow

Splash -> Login -> Dashboard -> Warning -> Progress -> Profile

---

## 7. Screen Requirements

### 1. Splash Screen
- Prima logo
- App name
- Loading state

### 2. Login Screen
- NIM input
- Password input
- Login button
- Forgot password link

### 3. Dashboard
- Greeting user
- GPA/IPK summary
- SKS summary
- Risk level card
- Quick actions

### 4. Warning Screen
- Graduation probability
- Risk label (Low / Medium / High)
- Risk reasons
- Recommendation card

### 5. Progress Screen
- SKS progress bar
- IPS per semester chart
- Current semester info

### 6. Profile Screen
- Name
- NIM
- Study program
- Faculty
- Logout

---

## 8. Navigation Structure

Bottom Navigation:
- Home
- Progress
- Warning
- Profile

---

## 9. UI / Design System

### Theme
Modern, clean, academic, startup style.

### Colors
- Primary Blue
- Success Green
- Warning Yellow
- Danger Red
- Neutral Gray

### Fonts
- Poppins / Inter / Roboto

### Design Rules
- Minimalist
- Readable
- Spacious layout
- Card-based UI
- Rounded corners
- Consistent spacing

---

## 10. Success Criteria for Presentation

The project is successful if reviewers understand that Prima can:
- Show academic status clearly
- Give early warnings
- Improve student awareness
- Support graduation on time

---

## 11. Development Phases

### Phase 1 — Branding
- Finalize logo
- Define colors
- Define typography
- Create icon set

### Phase 2 — Design System
- Buttons
- Inputs
- Cards
- Navigation bar
- Charts style
- Spacing rules

### Phase 3 — MVP UI Screens
- Splash
- Login
- Dashboard
- Warning
- Progress
- Profile

### Phase 4 — Interactive Prototype
- Clickable navigation in Figma
- Screen transitions
- Demo ready prototype

### Phase 5 — Presentation Assets
- Pitch deck
- Problem & solution slides
- UI showcase slides
- Benefits & future roadmap

### Phase 6 — Functional App (Future)
- Android Studio implementation
- Kotlin + Jetpack Compose
- Backend API integration
- Authentication
- Data sync

### Phase 7 — Smart Features (Future)
- Random Forest prediction API
- Explainable AI reasons
- Push notifications
- Advisor dashboard

---

## 12. Suggested Tech Stack (Future)

### Mobile
- Kotlin
- Jetpack Compose
- Android Studio

### Backend
- FastAPI / Laravel / Node.js

### Database
- PostgreSQL / MySQL

### ML Service
- Python
- Scikit-learn

---

## 13. Coding Architecture Context

### Recommended App Architecture
- MVVM (Model View ViewModel)
- Repository Pattern
- Single Activity Architecture
- Navigation Component / Compose Navigation
- State-driven UI

### Suggested Modules
- app
- core/ui
- core/network
- core/common
- feature/auth
- feature/dashboard
- feature/warning
- feature/progress
- feature/profile

### Package Structure Example
- com.prima.app
- com.prima.core.ui
- com.prima.core.data
- com.prima.core.network
- com.prima.feature.auth
- com.prima.feature.dashboard
- com.prima.feature.warning
- com.prima.feature.progress
- com.prima.feature.profile

### Data Layers
- UI Layer
- Domain Layer
- Data Layer
- Remote Source
- Local Cache (future)

### State Management
Use UiState pattern:
- Loading
- Success
- Empty
- Error

### Navigation Routes
- splash
- login
- dashboard
- warning
- progress
- profile

### Reusable Components
- PrimaTopBar
- PrimaBottomNav
- StatCard
- RiskBadge
- ProgressCard
- ChartCard
- PrimaryButton
- EmptyStateView
- ErrorStateView

## 14. Folder Context for Vibe Coding

When generating code later, prioritize:
- Clean MVVM architecture
- Reusable UI components
- Material Design 3
- Responsive layouts
- Dummy JSON data first
- Easy API replacement later

---

## 15. API & Backend Context

### Initial Development Mode
Use dummy local data first, then swap to API later.

### Dummy Data Example
- Student name
- NIM
- GPA/IPK
- Total SKS
- Semester
- Risk score
- Risk reasons

### Future API Endpoints
- POST /auth/login
- GET /student/profile
- GET /student/dashboard
- GET /student/progress
- GET /student/warnings
- GET /predict/{nim}

### Example Response
```json
{
  "name": "Rafael",
  "nim": "2410512082",
  "ipk": 3.25,
  "sks": 96,
  "risk": "medium"
}
```

### Security Rules
- JWT token auth
- HTTPS only
- Store token securely
- Role-based access

## 16. Prompt Context for AI Coding Assistants

Build Prima as a modern Android student companion app for early academic warning. Use clean architecture, polished UI, smooth navigation, card dashboards, charts, and dummy data first. Prioritize presentation quality and scalability.

---

## 17. Android UI Coding Standards

### General Rules
- Use Material Design 3
- Use dark mode ready theme
- Avoid hardcoded strings
- Use string resources
- Use dimension resources
- Support different screen sizes

### Compose Rules
- Stateless composables when possible
- Preview for each screen
- Reusable components first
- Separate UI and business logic

### XML Rules (if XML used)
- ConstraintLayout preferred
- Separate drawable resources
- Reusable styles.xml

### Performance Rules
- LazyColumn for lists
- Remember state properly
- Minimize recomposition

## 18. Testing Context

### Minimum Testing
- Navigation works
- Screen renders correctly
- Dummy data shown correctly
- Orientation safe
- No crashes on launch

## 19. Current Priority

1. Finalize UI screens
n2. Build interactive prototype
3. Prepare presentation
4. Convert to Android code later

