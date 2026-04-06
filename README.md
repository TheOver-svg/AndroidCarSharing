# 🚗 Carsharing App - Android (University Course Project)

A modern mobile application for carsharing, developed as a university course project. This project focuses on delivering a smooth UI/UX and utilizing a 100% declarative approach to Android UI development.

## 🌟 Key Features
* **Interactive Map:** View, search, and locate available cars nearby using Google Maps.
* **Custom BottomSheet:** Smooth, gesture-driven transitions between the map view and detailed car information.
* **Dynamic Details:** View 3D renders of vehicles and responsive battery/fuel level indicators.
* **Booking System:** A quick and secure vehicle rental process.

## 🛠 Tech Stack

### Mobile App (Android)
* **Language:** [Kotlin](https://kotlinlang.org/)
* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (100% declarative UI)
* **Architecture:** MVVM (Model-View-ViewModel)
* **Maps:** [Google Maps SDK for Android](https://developers.google.com/maps/documentation/android-sdk)

### Backend & Database
* **API:** [Python FastAPI](https://fastapi.tiangolo.com/) (Fast and scalable backend for core business logic)
* **Database & Auth:** [Firebase](https://firebase.google.com/) (Firestore for real-time data synchronization and Firebase Authentication)

## 🚀 How to Run Locally

1. Clone this repository:
   ```bash
   git clone [https://github.com/TheOver-svg/AndroidCarSharing.git)
   ```
2. Open the project in **Android Studio**.
3. Configure Google Maps:
   * Obtain an API key from the Google Cloud Console.
   * Add it to your `local.properties` file (create the file in the root directory if it doesn't exist):
     ```properties
     MAPS_API_KEY=your_secret_api_key_here
     ```
4. Configure Firebase:
   * Create a new project in the Firebase Console.
   * Download the `google-services.json` file and place it inside the `app/` directory.
5. Sync project with Gradle files and click **Run** (Shift + F10).

## 👨‍💻 Author
**Yaroslav** — [LinkedIn](https://www.linkedin.com/in/yaroslav-turlai/) | [GitHub](https://github.com/TheOver-svg)
