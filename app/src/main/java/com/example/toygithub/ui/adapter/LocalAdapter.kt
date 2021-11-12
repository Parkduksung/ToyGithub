package com.example.toygithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toygithub.R
import com.example.toygithub.databinding.ItemLocalBinding
import com.example.toygithub.room.GithubEntity

class LocalAdapter : RecyclerView.Adapter<LocalViewHolder>() {

    private val githubEntityList = mutableListOf<GithubEntity>()

    private lateinit var onItemClick: (githubEntity: GithubEntity) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalViewHolder =
        LocalViewHolder(parent)

    override fun onBindViewHolder(holder: LocalViewHolder, position: Int) {
        holder.bind(githubEntityList[position], onItemClick)
    }

    override fun getItemCount(): Int =
        githubEntityList.size

    fun setOnItemClickListener(listener: (githubEntity: GithubEntity) -> Unit) {
        onItemClick = listener
    }

    fun addAll(list: List<GithubEntity>) {
        githubEntityList.addAll(list)
        notifyDataSetChanged()
    }

    fun add(entity: GithubEntity) {
        githubEntityList.add(entity)
        notifyDataSetChanged()
    }

    fun delete(entity: GithubEntity) {
        githubEntityList.remove(githubEntityList.first { it.id == entity.id })
        notifyDataSetChanged()
    }

    fun clear() {
        githubEntityList.clear()
        notifyDataSetChanged()
    }
}


class LocalViewHolder(viewGroup: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_local, viewGroup, false)
    ) {

    private val binding = ItemLocalBinding.bind(itemView)

    fun bind(item: GithubEntity, onItemClick: (githubEntity: GithubEntity) -> Unit) {

        with(binding) {

            Glide.with(itemView.context).load(item.avatar_url).into(image)

            user.text = item.login

            deleteBookmark.setOnClickListener {
                onItemClick(item)
            }
        }

    }
}
