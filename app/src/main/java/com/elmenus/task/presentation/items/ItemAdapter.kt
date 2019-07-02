package com.elmenus.task.presentation.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.elmenus.base.app.AppRecyclerView
import com.elmenus.task.R
import com.elmenus.task.app.GlideApp
import com.elmenus.task.domain.model.Item
import kotlinx.android.synthetic.main.card_row_item.view.*

class ItemAdapter : AppRecyclerView.Adapter<Item, ItemAdapter.ViewHolder>() {

    private var listener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_row_item, parent, false)
        )
    }

    inner class ViewHolder(itemView: View) : AppRecyclerView.ViewHolder<Item>(itemView) {
        override fun bind(position: Int, obj: Item) {
            itemView.name.text = obj.name
            itemView.image?.let {
                itemView.setOnClickListener { listener?.onClickItem(obj, itemView.image) }

                GlideApp.with(itemView)
                    .load(obj.photo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(it)
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.listener = onClickListener
    }

    interface OnClickListener {
        fun onClickItem(obj: Item, imageView: ImageView)
    }
}
