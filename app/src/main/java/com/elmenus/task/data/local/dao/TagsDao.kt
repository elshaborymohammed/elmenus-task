package com.elmenus.task.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elmenus.task.data.local.entity.TagRoom
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * this responsible for manipulate {@link TagRoom} into database
 * */
@Dao
interface TagsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(tags: List<TagRoom>)

    @Query("SELECT * FROM tag")
    fun get(): List<TagRoom>
}
