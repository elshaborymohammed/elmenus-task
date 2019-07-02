package com.elmenus.task.domain.usecase

import com.elmenus.task.domain.model.Item
import com.elmenus.task.domain.protocol.IItemsProtocol
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ItemsUseCase responsible for get Items via IItemsProtocol
 */
@Singleton
class ItemsUseCase @Inject constructor(private val protocol: IItemsProtocol) {

    fun get(tagName: String): Single<List<Item>> {
        return this.protocol.get(tagName)
    }

    fun getOne(name: String): Single<Item> {
        return this.protocol.getOne(name)
    }
}