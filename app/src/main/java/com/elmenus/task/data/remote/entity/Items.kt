package com.elmenus.task.data.remote.entity

import com.elmenus.task.domain.model.Item
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("items")
    @Expose
    var data: List<Item>
)
