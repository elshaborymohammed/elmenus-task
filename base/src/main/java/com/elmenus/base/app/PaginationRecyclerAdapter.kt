package com.elmenus.base.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationRecyclerAdapter<T> : AppRecyclerView.Adapter<T, RecyclerView.ViewHolder>() {

    companion object {
        protected var ITEM = 0
        protected var LOADING = 1
    }
}
