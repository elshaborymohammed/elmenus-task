package com.elmenus.task.data.repository

import com.elmenus.base.executor.WorkerThread
import com.elmenus.task.data.local.dao.TagsDao
import com.elmenus.task.data.local.entity.TagRoom
import com.elmenus.task.data.remote.requester.RequesterTagsApi
import com.elmenus.task.domain.model.Tag
import com.elmenus.task.domain.protocol.ITagsProtocol
import io.reactivex.Single
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Singleton

/**
 * this Repository responsible for manipulate Tags from remote api and local database
 */
@Singleton
class TagsRepository @Inject constructor(
    private val api: RequesterTagsApi,
    private val dao: TagsDao,
    private val workerThread: WorkerThread
) : ITagsProtocol {

    override fun get(): Single<List<Tag>> {
        return Single.create { emit ->
            try {
                val room = dao.get()

                if (room.isNotEmpty()) {
                    val tags = ArrayList<Tag>()
                    room.forEach {
                        tags.add(Tag(it.name, it.photo))
                    }
                    emit.onSuccess(tags)
                } else {
                    fetchFromApi(1)
                        .subscribeOn(workerThread.scheduler)
                        .observeOn(workerThread.scheduler)
                        .subscribe(Consumer {
                            onSuccess().accept(it)
                            emit.onSuccess(it)
                        }, Consumer { emit.onError(it) })
                }
            } catch (it: Throwable) {
                emit.onError(it)
            }
        }
    }

    override fun get(page: Int): Single<List<Tag>> {
        return fetchFromApi(page)
            .doOnSuccess(onSuccess())
    }

    private fun onSuccess(): Consumer<List<Tag>> {
        return Consumer { list ->
            val tags: ArrayList<TagRoom> = ArrayList()
            list.forEach {
                tags.add(TagRoom(it.name, it.photo))
            }

            dao.insert(tags.toList())
        }
    }

    private fun fetchFromApi(page: Int): Single<List<Tag>> {
        return api.get(page).map { it.data }
    }
}