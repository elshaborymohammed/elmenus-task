package com.elmenus.task.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("tagName")
    @Expose
    var name: String,
    @SerializedName("photoURL")
    @Expose
    var photo: String
){
    override fun toString(): String {
        return "Tag(name=$name, photo=$photo)"
    }
}
