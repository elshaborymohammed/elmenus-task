package com.elmenus.task.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmenus.task.data.local.entity.ItemRoom
import io.reactivex.Single

/**
 * this responsible for manipulate {@link ItemRoom} into database
 * */
@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<ItemRoom>)

    @Query("SELECT * FROM item WHERE tag_name=:tagName")
    fun get(tagName: String): List<ItemRoom>

    @Query("SELECT * FROM item WHERE name=:name")
    fun getOne(name: String): Single<ItemRoom>
}
