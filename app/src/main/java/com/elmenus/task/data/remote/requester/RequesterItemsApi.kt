package com.elmenus.task.data.remote.requester

import com.elmenus.task.data.remote.api.ItemsApi
import com.elmenus.task.data.remote.entity.Items
import io.reactivex.Single
import retrofit2.Retrofit

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequesterItemsApi @Inject
constructor(private val retrofit: Retrofit) : ItemsApi {

    override operator fun get(tagName: String): Single<Items> {
        return retrofit.create(ItemsApi::class.java).get(tagName)
    }
}
