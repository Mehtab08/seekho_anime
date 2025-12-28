package com.crusader.seekho_anime

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crusader.seekho_anime.data.api.RetrofitInstance
import com.crusader.seekho_anime.data.db.AnimeDatabase
import com.crusader.seekho_anime.data.db.AnimeEntity
import com.crusader.seekho_anime.data.repository.AnimeRepository
import com.crusader.seekho_anime.ui.main.AnimeAdapter
import com.crusader.seekho_anime.ui.detail.DetailActivity
import com.crusader.seekho_anime.ui.main.AnimeListViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: AnimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("APP", "MainActivity started")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = AnimeAdapter { anime ->
            Log.d("NAV_TEST", "Starting DetailActivity with id=${anime.id}")
            startActivity(
                Intent(this, DetailActivity::class.java)
                    .putExtra("id", anime.id)
            )
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        val db = AnimeDatabase.get(this)
        val repo = AnimeRepository(RetrofitInstance.api, db.animeDao())
        val vm = AnimeListViewModel(repo)

        lifecycleScope.launch {
            vm.animeList.collect { list ->
                Log.d("DEBUG", "Received ${list.size} items")
                adapter.submit(list)
            }
        }
    }
}
