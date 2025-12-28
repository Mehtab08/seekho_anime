package com.crusader.seekho_anime.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.crusader.seekho_anime.R
import com.crusader.seekho_anime.data.db.AnimeEntity
import com.crusader.seekho_anime.ui.detail.DetailActivity

class AnimeAdapter(
    private val onClick: (AnimeEntity) -> Unit
) : RecyclerView.Adapter<AnimeAdapter.VH>() {

    private val list = mutableListOf<AnimeEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun submit(data: List<AnimeEntity>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    class VH(val v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_anime, parent, false)
        return VH(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val anime = list[position]

        holder.v.findViewById<TextView>(R.id.title).text = anime.title
        holder.v.findViewById<TextView>(R.id.episodes).text = "Episodes: ${anime.episodes ?: "N/A"}"
        holder.v.findViewById<TextView>(R.id.rating).text = "Rating: ${anime.score ?: "N/A"}"

        val imageView = holder.v.findViewById<ImageView>(R.id.img)

        Glide.with(imageView.context)
            .load(anime.imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(imageView)


        holder.v.setOnClickListener { onClick(anime) }

        holder.v.setOnClickListener {
            Log.d("CLICK_TEST", "Clicked anime id = ${anime.id}")
            val context = holder.v.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", anime.id)
            context.startActivity(intent)
        }
    }
}

