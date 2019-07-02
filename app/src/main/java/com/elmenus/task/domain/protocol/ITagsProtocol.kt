package com.elmenus.task.domain.protocol

import com.elmenus.task.domain.model.Tag
import io.reactivex.Single

/**
 * this Protocol responsible for manipulate Tags
 */
interface ITagsProtocol {
    fun get(): Single<List<Tag>>
    fun get(page: Int): Single<List<Tag>>
}