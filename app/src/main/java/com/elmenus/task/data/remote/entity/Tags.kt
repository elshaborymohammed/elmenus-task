package com.elmenus.task.data.remote.entity

import com.elmenus.task.domain.model.Tag
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tags(
    @SerializedName("tags")
    @Expose
    var data: List<Tag>
)
