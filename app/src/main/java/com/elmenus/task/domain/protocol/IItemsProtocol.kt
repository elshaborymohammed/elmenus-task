package com.elmenus.task.domain.protocol

import com.elmenus.task.domain.model.Item
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * this Protocol responsible for manipulate Items
 */
interface IItemsProtocol {
    fun get(tagName: String): Single<List<Item>>
    fun getOne(name: String): Single<Item>
}