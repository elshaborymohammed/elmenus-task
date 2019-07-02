package com.elmenus.task.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    @Expose
    var id: Long,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("photoUrl")
    @Expose
    var photo: String,
    @SerializedName("description")
    @Expose
    var description: String
)
