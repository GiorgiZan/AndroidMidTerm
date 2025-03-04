# GymBro

**GymBro** is an Android application designed to assist users with their gym workouts. It offers features for tracking progress, selecting workout difficulty, and provides an AI-powered chat assistant to guide users in their fitness journey.

## Purpose

GymBro was created to help users easily track their fitness progress, get workout suggestions, and interact with an AI-powered chat assistant for fitness-related advice. The app is designed for users of all fitness levels, with customizable difficulty settings and personalized workout routines.

## Features

- **Login & Registration**: Users can sign up with their email and password or log in to access their personal information and workout plans.
- **Profile Management**: Users can input their details (weight, age, height) to personalize their workout experience.
- **AI Chat Assistant (GymBro)**: Interact with GymBro, the AI-powered assistant, for personalized workout suggestions and fitness tips.
- **Home**: Displays workout suggestions based on the user's profile and progress.
- **Difficulty Selector**: Choose from beginner, intermediate, or advanced workout routines to match your fitness level.
- **Workouts**: Access workout plans based on selected difficulty and user profile.

## Technologies Used

- **Firebase**: Used for user authentication (email/password login and registration) and storing user data (profile).
- **Data store**: Local data storage for user progress, workout history, and preferences.
- **Hilt**: Dependency Injection framework for managing dependencies like `FirebaseAuth` and `FirebaseFirestore`.
- **Kotlin**: Main programming language for Android development.
- **MVVM Architecture**: Ensures a clean separation of concerns and maintainable code structure.
- **Retrofit**: Used for handling API calls.
- **Kotlin Serialization**: JSON parsing for network operations.
