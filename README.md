# 🎥 Movie App

Movie App is an Android application built with **Kotlin** and **Jetpack Compose**, offering a sleek and modern interface for browsing and discovering movies. It fetches data from [The Movie Database (TMDb)](https://www.themoviedb.org/) API, providing detailed information about movies, trailers, and more.

---

## 📱 Features

- **Modern UI with Jetpack Compose**: Implements Material 3 for a polished, consistent design.
- **Splash Screen**: Uses the Android Splash Screen API for a seamless startup experience.
- **Dependency Injection**: Powered by Dagger-Hilt for modular and testable architecture.
- **Offline Support**: Caches movie data locally using Room Database.
- **Asynchronous Programming**: Coroutines for smooth and responsive data fetching.
- **Image Loading**: Leverages Coil for efficient image handling and caching.
- **Clean Architecture**: Adheres to the MVVM (Model-View-ViewModel) design pattern.

---

## 🛠️ Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Dagger-Hilt** (Dependency Injection)
- **Room** (Local Database)
- **Coroutines** (Asynchronous Programming)
- **Coil** (Image Loading)
- **TMDb API** (Data Source)

---

## 🚀 How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/chris2y/MovieApp.git
   ```

2. Open the project in Android Studio.

3. Add your TMDb API Key to the util calass constants file:

   ```constants
   TMDB_API_KEY=your_api_key_here
   ```

4. Build and run the project on an emulator or a physical device.

---

## 🎥 Demo Video

[![Watch the video](https://img.youtube.com/vi/your_video_id/maxresdefault.jpg)](https://www.youtube.com/watch?v=your_video_id)

