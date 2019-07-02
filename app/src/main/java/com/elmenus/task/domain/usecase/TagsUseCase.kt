package com.elmenus.task.domain.usecase

import com.elmenus.task.domain.model.Tag
import com.elmenus.task.domain.protocol.ITagsProtocol
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TagsUseCase responsible for get Items via ITagsProtocol
 */
@Singleton
class TagsUseCase @Inject constructor(private val protocol: ITagsProtocol) {

    fun get(): Single<List<Tag>> {
        return this.protocol.get()
    }

    fun get(page: Int): Single<List<Tag>> {
        return this.protocol.get(page)
    }
}