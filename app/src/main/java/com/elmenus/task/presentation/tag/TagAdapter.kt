package com.elmenus.task.presentation.tag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.elmenus.base.app.AppRecyclerView
import com.elmenus.task.R
import com.elmenus.task.app.GlideApp
import com.elmenus.task.domain.model.Tag
import kotlinx.android.synthetic.main.card_row_load.view.*
import kotlinx.android.synthetic.main.card_row_tag.view.*


open class TagAdapter : AppRecyclerView.Adapter<Tag, RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    private var selectedIndex: Int = 0
    private var noLoadMore: Boolean = false
    private var noLoadMoreWithError: Boolean = false

    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var listener: OnClickListener? = null

    private fun inflate(parent: ViewGroup, @LayoutRes layoutRes: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    }

    override fun itemDecorations(): Array<RecyclerView.ItemDecoration> {
        return arrayOf(AppRecyclerView.SpacesItemDecoration.Linear(context, 0, 4, 4, 0))
    }

    override fun layoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) LOADING else ITEM
    }

    override fun getItemCount(): Int {
        return if (null != data && data.size > 0) data.size + 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (ITEM == viewType)
            ItemViewHolder(inflate(parent, R.layout.card_row_tag))
        else
            LoadingViewHolder(inflate(parent, R.layout.card_row_load))
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == LOADING && viewHolder is LoadingViewHolder) {

            if (null != onLoadMoreListener && (!noLoadMore)) {
                viewHolder.itemView.progressBar.visibility = View.VISIBLE
                viewHolder.itemView.retry.visibility = View.GONE
                onLoadMoreListener?.onLoadMore((itemCount - 1) / 8 + 1)
            } else if (noLoadMoreWithError) {
                viewHolder.itemView.progressBar.visibility = View.GONE
                viewHolder.itemView.retry.visibility = View.VISIBLE
                viewHolder.itemView.retry.setOnClickListener {
                    noLoadMore = false
                    noLoadMoreWithError = false
                    onLoadMoreListener?.onLoadMore((itemCount - 1) / 8 + 1)
                }
            } else {
                viewHolder.itemView.progressBar.visibility = View.GONE
                viewHolder.itemView.retry.visibility = View.GONE
            }

        } else if (getItemViewType(position) == ITEM) {
            if (viewHolder is ItemViewHolder) {
                viewHolder.bind(position, get(position))
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.listener = onClickListener
    }

    interface OnClickListener {
        fun onClickTag(obj: Tag)
    }


    fun noMoreData() {
        noLoadMore = true
        noLoadMoreWithError = false
        notifyItemChanged(itemCount - 1)
    }

    fun noMoreDataWithError() {
        noLoadMore = true
        noLoadMoreWithError = true
        notifyItemChanged(itemCount - 1)
    }

    fun setOnLoadMoreListener(loadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = loadMoreListener
    }

    interface OnLoadMoreListener {
        fun onLoadMore(page: Int)
    }

    inner class ItemViewHolder(itemView: View) : AppRecyclerView.ViewHolder<Tag>(itemView) {

        public override fun bind(position: Int, obj: Tag) {
            itemView.let {
                it.setOnClickListener {
                    notifyItemChanged(selectedIndex)
                    selectedIndex = position
                    listener?.onClickTag(obj)
                    notifyItemChanged(position)
                }
                it.select.isSelected = position == selectedIndex
                it.name.text = obj.name
                GlideApp.with(itemView)
                    .load(obj.photo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop()
                    .into(it.image)
            }
        }
    }

    inner class LoadingViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }
}
