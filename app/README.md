🎌 Seekho Anime App

An Android application built using Kotlin + MVVM that displays top anime and detailed anime information using the Jikan API (MyAnimeList).

📱 Features Implemented
✅ Anime List Screen

Displays Top Anime

RecyclerView with images & titles

Click on an anime to open detail page

✅ Anime Detail Screen

🎥 Trailer video player (YouTube embed)

🖼️ Poster image fallback if trailer unavailable

📌 Anime Title

📝 Plot / Synopsis

🎭 Genre(s)

👥 Main Cast (Top 5 characters)

📺 Number of Episodes

⭐ Rating

✅ Architecture & Tech

MVVM Architecture

Retrofit + Gson

Kotlin Coroutines & StateFlow

Glide for image loading

Lifecycle-aware data collection

🌐 API Used

Jikan API (Unofficial MyAnimeList API)

https://api.jikan.moe/v4/

🧠 Assumptions Made

Only top anime list is displayed (pagination not implemented)

Main cast limited to first 5 characters for simplicity

Trailer source assumed to be YouTube

Network connection is required (offline mode not implemented)

⚠️ Known Limitations

❌ No offline caching (Room integration incomplete)

❌ No pagination for large lists

❌ Basic UI (focus was functionality over design)

❌ Error handling could be improved for API failures

❌ ExoPlayer not used (WebView used for trailer)

🛠️ Tech Stack

Language: Kotlin

Architecture: MVVM

Networking: Retrofit

Image Loading: Glide

Async: Coroutines + Flow

UI: XML
