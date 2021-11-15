package com.example.toygithub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.toygithub.BR
import com.example.toygithub.R
import com.example.toygithub.databinding.ItemApiDataBinding
import com.example.toygithub.databinding.ItemApiTitleBinding
import com.example.toygithub.room.GithubEntity

class ApiAdapter : RecyclerView.Adapter<BaseApiViewHolder<*>>() {


    private val githubEntityList = mutableListOf<GithubData>()

    private lateinit var onItemClick: (entity: GithubEntity, isBookmark: Boolean) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseApiViewHolder<*> =
        sortViewHolder(parent, viewType, onItemClick)

    override fun getItemViewType(position: Int): Int =
        githubEntityList[position].type.ordinal

    override fun onBindViewHolder(holder: BaseApiViewHolder<*>, position: Int) {
        when (holder) {
            is ApiTitleViewHolder -> {
                githubEntityList[position].title?.let { holder.bind(it) }
            }

            is ApiDataViewHolder -> {
                githubEntityList[position].data?.let { holder.bind(it) }
            }
        }
    }

    override fun getItemCount(): Int =
        githubEntityList.size

    fun setOnItemClickListener(listener: (entity: GithubEntity, isBookmark: Boolean) -> Unit) {
        onItemClick = listener
    }


    //Grouping 하여 각 그룹의 맨처음에서 title 을 넣어주고 list 를 뒤이어 추가한다.
    fun addAll(list: List<GithubEntity>) {

        val _githubEntityList = mutableListOf<GithubData>()

        val toGroupingAndSortedList = list.groupBy { it.login!!.first() }.toSortedMap()

        toGroupingAndSortedList.forEach {
            _githubEntityList.add(GithubData(type = ApiAdapterType.TITLE, it.key.toString()))
            _githubEntityList.addAll(it.value.map { entity ->
                GithubData(
                    type = ApiAdapterType.DATA,
                    data = entity
                )
            })
        }
        githubEntityList.addAll(_githubEntityList)
        notifyDataSetChanged()
    }


    fun updateItem(entity: GithubEntity) {
        if (githubEntityList.any { it.data?.id == entity.id }) {
            val searchEntity = githubEntityList.first { it.data?.id == entity.id }
            val index = githubEntityList.indexOf(searchEntity)
            githubEntityList[index].data?.like = entity.like
            notifyDataSetChanged()
        }
    }

    fun clear() {
        githubEntityList.clear()
        notifyDataSetChanged()
    }

    companion object {

        fun sortViewHolder(
            parent: ViewGroup,
            viewType: Int,
            onItemClick: (item: GithubEntity, isBookmark: Boolean) -> Unit
        ): BaseApiViewHolder<*> {
            return when (viewType) {
                ApiAdapterType.TITLE.ordinal -> {
                    ApiTitleViewHolder(parent, R.layout.item_api_title)
                }

                ApiAdapterType.DATA.ordinal -> {
                    ApiDataViewHolder(parent, R.layout.item_api_data, onItemClick)
                }

                else -> {
                    ApiDataViewHolder(parent, R.layout.item_api_data, onItemClick)
                }
            }
        }
    }

}

abstract class BaseApiViewHolder<T : Any>(
    parent: ViewGroup,
    @LayoutRes layoutId: Int,
    private val onItemClick: ((Item: GithubEntity, isBookmark: Boolean) -> Unit)? = null
) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    ) {

    protected lateinit var binding: ViewDataBinding

    abstract fun bind(item: T)

}


class ApiDataViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutId: Int,
    private val onItemClick: ((item: GithubEntity, isBookmark: Boolean) -> Unit)? = null
) : BaseApiViewHolder<GithubEntity>(
    parent, layoutId, onItemClick
) {
    init {
        binding = ItemApiDataBinding.bind(itemView)
    }

    override fun bind(item: GithubEntity) {
        with(binding as ItemApiDataBinding) {
            setVariable(BR.githubEntity, item)
            executePendingBindings()
            bookmark.isChecked = item.like!!
            bookmark.setOnClickListener {
                onItemClick?.let { it1 ->
                    it1(
                        item,
                        bookmark.isChecked
                    )
                }
            }
        }

    }
}

class ApiTitleViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutId: Int
) : BaseApiViewHolder<String>(
    parent, layoutId
) {
    init {
        binding = ItemApiTitleBinding.bind(itemView)
    }

    override fun bind(item: String) {
        binding.run {
            setVariable(BR.title, item)
            executePendingBindings()
        }
    }
}


data class GithubData(
    val type: ApiAdapterType,
    val title: String? = null,
    val data: GithubEntity? = null
)


enum class ApiAdapterType(type: Int) {
    TITLE(0), DATA(1)
}


