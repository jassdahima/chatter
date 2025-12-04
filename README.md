# Chatter - A Real-Time Android Chat Application

Chatter is a modern, real-time chat application for Android, built entirely with Kotlin and Jetpack Compose. This project demonstrates a clean, scalable architecture using modern Android development practices, including Firebase for backend services and Koin for dependency injection.

## ✨ Features

- **Real-Time Messaging:** Send and receive messages instantly within chat channels.
- **User Authentication:** Secure sign-up and sign-in functionality using Firebase Authentication (Email/Password).
- **Channel Management:** View and create different chat channels.
- **Message & Channel Deletion:** Long-press on messages or channels to delete them with a confirmation dialog.
- **Pinned Channels:** Pin important channels to the top of the list for easy access.
- **Modern UI:** A sleek, dark-themed UI built entirely with Jetpack Compose, providing a reactive and engaging user experience.
- **Image Loading:** Efficiently loads network images using Coil.
- **Dependency Injection:** A clean and maintainable codebase structured with Koin for dependency injection.

## 📸 Screenshots

![Sign Up Screen](screenshots/signup-screen.png)


## 🛠️ Tech Stack & Architecture

This project is built using the following modern technologies and architectural principles:

-   **UI:** 100% [Jetpack Compose](https://developer.android.com/jetpack/compose) for building the user interface.
-   **Backend:** [Firebase Realtime Database](https://firebase.google.com/products/realtime-database) for live data synchronization and [Firebase Authentication](https://firebase.google.com/products/auth) for user management.
-   **Architecture:** Follows a modern MVVM (Model-View-ViewModel) architecture.
-   **Dependency Injection:** [Koin](https://insert-koin.io/) is used for managing dependencies throughout the app.
-   **Navigation:** [Jetpack Navigation for Compose](https://developer.android.com/jetpack/compose/navigation) handles all screen transitions.
-   **Image Loading:** [Coil](https://coil-kt.github.io/coil/) for fast and efficient image loading.
-   **Language:** Written entirely in [Kotlin](https://kotlinlang.org/).

## 🚀 How to Run

To run this project on your own, you will need to set up your own Firebase project.

1.  **Clone the repository:**
    
