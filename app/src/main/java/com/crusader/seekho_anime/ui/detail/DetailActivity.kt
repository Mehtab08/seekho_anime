package com.crusader.seekho_anime.ui.detail

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra("id", -1)
        if (id == -1) {
            finish()
            return
        }

        val db = AnimeDatabase.get(this)
        val repo = AnimeRepository(RetrofitInstance.api, db.animeDao())

        val vm = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AnimeDetailViewModel(repo) as T
                }
            }
        )[AnimeDetailViewModel::class.java]

        vm.load(id)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.detail.collect { response ->
                    response ?: return@collect
                    val anime = response.data
//
//                    findViewById<TextView>(R.id.title).text = anime.title
//                    findViewById<TextView>(R.id.plot).text = anime.synopsis


                    val title = findViewById<TextView>(R.id.title)
                    val plot = findViewById<TextView>(R.id.plot)
                    val rating = findViewById<TextView>(R.id.rating)
                    val episodes = findViewById<TextView>(R.id.episodes)
                    val image = findViewById<ImageView>(R.id.poster)

                    title.text = anime.title
                    plot.text = anime.synopsis ?: "No description"
                    rating.text = "Rating: ${anime.score ?: "N/A"}"
                    episodes.text = "Episodes: ${anime.episodes ?: "N/A"}"

                    Glide.with(this@DetailActivity)
                        .load(anime.images.jpg.image_url)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(image)
                }
            }
        }
    }
}
