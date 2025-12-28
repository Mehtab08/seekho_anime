package com.crusader.seekho_anime.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crusader.seekho_anime.R
import com.crusader.seekho_anime.data.api.RetrofitInstance
import com.crusader.seekho_anime.data.db.AnimeDatabase
import com.crusader.seekho_anime.data.repository.AnimeRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.Glide


class DetailActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val animeId = intent.getIntExtra("id", -1)
        if (animeId == -1) {
            finish()
            return
        }

        // ✅ Repository
        val db = AnimeDatabase.get(this)
        val repo = AnimeRepository(RetrofitInstance.api, db.animeDao())

        // ✅ ViewModel
        val vm = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AnimeDetailViewModel(repo) as T
                }
            }
        )[AnimeDetailViewModel::class.java]

        vm.load(animeId)

        val title = findViewById<TextView>(R.id.title)
        val plot = findViewById<TextView>(R.id.plot)
        val rating = findViewById<TextView>(R.id.rating)
        val episodes = findViewById<TextView>(R.id.episodes)
        val genres = findViewById<TextView>(R.id.genres)
        val cast = findViewById<TextView>(R.id.cast)
        val poster = findViewById<ImageView>(R.id.poster)
        val trailerWeb = findViewById<WebView>(R.id.trailerWeb)

        trailerWeb.webViewClient = WebViewClient()
        trailerWeb.settings.javaScriptEnabled = true

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                vm.detail.collect { response ->
                    val anime = response ?: return@collect

                    title.text = anime.title
                    plot.text = anime.synopsis ?: "No description"
                    rating.text = "⭐ ${anime.score ?: "N/A"}"
                    episodes.text = "Episodes: ${anime.episodes ?: "N/A"}"
                    genres.text = if (anime.genres.isNotEmpty())
                        "Genres: ${anime.genres.joinToString { it.name }}"
                    else
                        "Genres: N/A"

                    cast.text = "Cast: Not available"

                    val youtubeId = anime.trailer?.youtube_id

                    if (!youtubeId.isNullOrEmpty()) {
                        trailerWeb.visibility = View.VISIBLE
                        poster.visibility = View.GONE

                        val html = """
                            <html>
                              <body style="margin:0">
                                <iframe width="100%" height="100%"
                                  src="https://www.youtube.com/embed/$youtubeId"
                                  frameborder="0"
                                  allowfullscreen>
                                </iframe>
                              </body>
                            </html>
                        """.trimIndent()

                        trailerWeb.loadData(html, "text/html", "utf-8")

                    } else {
                        trailerWeb.visibility = View.GONE
                        poster.visibility = View.VISIBLE

                        Glide.with(this@DetailActivity)
                            .load(anime.images.jpg.image_url)
                            .placeholder(R.drawable.placeholder)
                            .into(poster)
                    }
                }
            }
        }
    }
}
