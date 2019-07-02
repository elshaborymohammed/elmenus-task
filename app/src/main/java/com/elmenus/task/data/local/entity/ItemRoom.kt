package com.elmenus.task.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * this class represent Item's table structure
 */
@Entity(tableName = "item")
data class ItemRoom(
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    var id: Long,
    @ColumnInfo(name = "tag_name")
    var tagName: String,
    @PrimaryKey
    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String,
    @ColumnInfo(name = "photoUrl")
    @SerializedName("photoUrl")
    @Expose
    var photo: String,
    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    var description: String
)
