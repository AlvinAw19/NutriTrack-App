# NutriTrack - AI-Powered Nutrition Companion

![App Logo](app/src/main/res/drawable/nutritracklogiremovebackground.png)

NutriTrack is an Android nutrition-tracking app that provides personalized dietary recommendations using AI-driven insights. It helps users understand their eating patterns through the HEIFA scoring system and offers motivational tips to improve their diet.

## Key Features

- **Personalized Nutrition Scoring**: HEIFA-based assessment of 14 dietary categories
- **AI-Powered Insights**: Gemini AI integration for personalized recommendations
- **Food Quality Analysis**: Detailed breakdown of nutritional intake
- **Clinician Dashboard**: Advanced analytics for healthcare professionals
- **Motivational Coaching**: Daily encouragement based on eating patterns
- **Secure Authentication**: Room Database with encrypted credentials

## Technical Implementation

### Core Components

- **Authentication System**:
  - Secure login/registration flow
  - Session management with `AuthManager`
  - Encrypted credential storage using Room Database

- **Data Pipeline**:
  - CSV data loading for initial patient records (`CSVLoader`)
  - Real-time score calculations
  - Historical data tracking

- **AI Integration**:
  - Gemini AI API for generating personalized tips
  - Two distinct AI modes:
    - Patient-facing motivational messages
    - Clinician-facing data pattern analysis

### Architecture

- **MVVM Pattern**: Separation of concerns with ViewModels
- **Jetpack Compose**: Modern declarative UI framework
- **Room Database**: Local data persistence
- **Retrofit**: Network operations for nutrition data

## Screens

1. **Authentication Flow**:
   - Login with ID/password
   - First-time registration
   - Clinician login (special access)

2. **Main App Screens**:
   - Home Dashboard (HEIFA score overview)
   - Detailed Insights (category breakdowns)
   - NutriCoach (AI recommendations)
   - Health Analytics (trends and comparisons)
   - Settings (account management)

3. **Questionnaire**:
   - Food intake preferences
   - Personal persona selection
   - Daily routine tracking

4. **Clinician Dashboard**:
   - Population health analytics
   - AI-powered pattern detection
   - Gender-based comparisons
