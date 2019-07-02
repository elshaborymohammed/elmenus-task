package com.elmenus.task.data.repository

import com.elmenus.base.executor.WorkerThread
import com.elmenus.task.data.local.dao.ItemsDao
import com.elmenus.task.data.local.entity.ItemRoom
import com.elmenus.task.data.remote.requester.RequesterItemsApi
import com.elmenus.task.domain.model.Item
import com.elmenus.task.domain.protocol.IItemsProtocol
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Singleton

/**
 * this Repository responsible for manipulate Items from remote api and local database
 */
@Singleton
class ItemsRepository @Inject constructor(
    private val api: RequesterItemsApi,
    private val dao: ItemsDao,
    private val workerThread: WorkerThread
) : IItemsProtocol {

    /**
     * get data from database first if it's empty get from remote api
     * and insert data that loaded from api into database
     */
    override fun get(tagName: String): Single<List<Item>> {
        return Single.create { emit ->
            try {
                val room = dao.get(tagName)

                if (room.isNotEmpty()) {
                    val items = ArrayList<Item>()
                    room.forEach {
                        items.add(Item(it.id, it.name, it.photo, it.description))
                    }
                    emit.onSuccess(items)
                } else {
                    fetchFromApi(tagName)
                        .subscribeOn(workerThread.scheduler)
                        .observeOn(workerThread.scheduler)
                        .subscribe(Consumer {
                            val listType = object : TypeToken<List<ItemRoom>>() {

                            }.type
                            val items: List<ItemRoom> = Gson().fromJson(Gson().toJson(it), listType)
                            items.forEach { obj -> obj.tagName = tagName }
                            dao.insert(items)
                            emit.onSuccess(it)
                        }, Consumer { emit.onError(it) })
                }
            } catch (it: Throwable) {
                emit.onError(it)
            }
        }
    }

    override fun getOne(name: String): Single<Item> {
        return dao.getOne(name).map { Item(id = it.id, name = it.name, photo = it.photo, description = it.description) }
    }

    /**
     * get data from remote api
     */
    private fun fetchFromApi(tagName: String): Single<List<Item>> {
        return api.get(tagName).map { it.data }
    }
}