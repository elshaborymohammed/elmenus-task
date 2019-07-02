package com.elmenus.task.presentation.items

import com.elmenus.base.app.AppViewModel
import com.elmenus.base.executor.MainThread
import com.elmenus.base.executor.WorkerThread
import com.elmenus.task.domain.model.Item
import com.elmenus.task.domain.usecase.ItemsUseCase
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemViewModel
@Inject constructor(
    private val useCase: ItemsUseCase,
    private val subscribeOn: WorkerThread,
    private val observeOn: MainThread
) : AppViewModel() {

    private var lastTagName = ""

    fun get(tagName: String): Single<List<Item>> {
        lastTagName=tagName
        return useCase.get(tagName)
            .subscribeOn(subscribeOn.scheduler)
            .observeOn(observeOn.scheduler)
            .doOnSubscribe(loadingOn())
            .doOnSuccess(loadingOff())
            .doOnError(loadingOff())
    }

    fun getLastOne(): Single<List<Item>>? {
        return if (!lastTagName.isNullOrEmpty()) {
            useCase.get(lastTagName)
                .subscribeOn(subscribeOn.scheduler)
                .observeOn(observeOn.scheduler)
                .doOnSubscribe(loadingOn())
                .doOnSuccess(loadingOff())
                .doOnError(loadingOff())
        } else null
    }

    fun getOne(name: String): Single<Item> {
        return useCase.getOne(name)
            .subscribeOn(subscribeOn.scheduler)
            .observeOn(observeOn.scheduler)
            .doOnSubscribe(loadingOn())
            .doOnSuccess(loadingOff())
            .doOnError(loadingOff())
    }
}