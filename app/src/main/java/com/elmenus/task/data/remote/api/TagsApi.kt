package com.elmenus.task.data.remote.api

import com.elmenus.task.data.remote.entity.Tags
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TagsApi {

    @GET("tags/{page}")
    fun get(@Path("page") page: Int): Single<Tags>
}