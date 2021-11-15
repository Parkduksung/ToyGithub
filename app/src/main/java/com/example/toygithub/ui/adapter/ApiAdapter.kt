package com.example.toygithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toygithub.R
import com.example.toygithub.databinding.ItemApiBinding
import com.example.toygithub.room.GithubEntity

class ApiAdapter : RecyclerView.Adapter<ApiViewHolder>() {

    private val githubEntityList = mutableListOf<GithubEntity>()

    private lateinit var onItemClick: (entity: GithubEntity, isBookmark: Boolean) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder =
        ApiViewHolder(parent)

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        holder.bind(githubEntityList[position], onItemClick)
    }

    override fun getItemCount(): Int =
        githubEntityList.size

    fun setOnItemClickListener(listener: (entity: GithubEntity, isBookmark: Boolean) -> Unit) {
        onItemClick = listener
    }

    fun addAll(list: List<GithubEntity>) {
        githubEntityList.addAll(list)
        notifyDataSetChanged()
    }

    fun updateItem(entity: GithubEntity) {
        if (githubEntityList.any { it.id == entity.id }) {
            val searchEntity = githubEntityList.first { it.id == entity.id }
            val index = githubEntityList.indexOf(searchEntity)
            githubEntityList[index] = entity.copy(like = !entity.like!!)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        githubEntityList.clear()
        notifyDataSetChanged()
    }
}


class ApiViewHolder(viewGroup: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_api, viewGroup, false)
    ) {

    private val binding = ItemApiBinding.bind(itemView)

    fun bind(
        entity: GithubEntity,
        onItemClick: (entity: GithubEntity, isBookmark: Boolean) -> Unit
    ) {
        with(binding) {
            Glide.with(itemView.context).load(entity.avatar_url).into(image)
            user.text = entity.login
            bookmark.isChecked = entity.like!!
            bookmark.setOnClickListener { onItemClick(entity, bookmark.isChecked) }
        }
    }
}

