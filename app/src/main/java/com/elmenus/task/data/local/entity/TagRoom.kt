package com.elmenus.task.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * this class represent Tag's table structure
 */
@Entity(tableName = "tag")
data class TagRoom(
    @PrimaryKey
    @SerializedName("tagName")
    var name: String,
    @ColumnInfo(name = "photoUrl")
    @SerializedName("photoURL")
    var photo: String
) {
    override fun toString(): String {
        return "TagRoom(name='$name', photo='$photo')"
    }
}
