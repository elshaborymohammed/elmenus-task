package com.elmenus.task.data.remote.api

import com.elmenus.task.data.remote.entity.Items
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemsApi {
    @GET("items/{tag}")
    fun get(@Path("tag") tagName: String): Single<Items>
}