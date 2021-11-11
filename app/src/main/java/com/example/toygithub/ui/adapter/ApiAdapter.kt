package com.example.toygithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toygithub.R
import com.example.toygithub.api.response.SearchResponseItem
import com.example.toygithub.databinding.ItemApiBinding

class ApiAdapter : RecyclerView.Adapter<ApiViewHolder>() {

    private val searchItemList = mutableListOf<SearchResponseItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder =
        ApiViewHolder(parent)

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        holder.bind(searchItemList[position])
    }

    override fun getItemCount(): Int =
        searchItemList.size

    fun addAll(list: List<SearchResponseItem>) {
        searchItemList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        searchItemList.clear()
        notifyDataSetChanged()
    }
}


class ApiViewHolder(viewGroup: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(R.layout.item_api, viewGroup, false)
    ) {

    private val binding = ItemApiBinding.bind(itemView)

    fun bind(item: SearchResponseItem) {

        with(binding) {

            Glide.with(itemView.context).load(item.avatar_url).into(image)

            user.text = item.login

        }

    }
}

