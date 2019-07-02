package com.elmenus.task.data.remote.requester

import com.elmenus.task.data.remote.api.TagsApi
import com.elmenus.task.data.remote.entity.Tags
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequesterTagsApi @Inject
constructor(private val retrofit: Retrofit) : TagsApi {

    override operator fun get(page: Int): Single<Tags> {
        return retrofit.create(TagsApi::class.java).get(page)
    }
}
