package com.elmenus.task.presentation.tag

import com.elmenus.base.app.AppViewModel
import com.elmenus.base.executor.MainThread
import com.elmenus.base.executor.WorkerThread
import com.elmenus.task.domain.model.Tag
import com.elmenus.task.domain.usecase.TagsUseCase
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagViewModel
@Inject constructor(
    private val useCase: TagsUseCase,
    private val subscribeOn: WorkerThread,
    private val observeOn: MainThread
) : AppViewModel() {

    fun get(): Single<List<Tag>> {
        return useCase.get()
            .subscribeOn(subscribeOn.scheduler)
            .observeOn(observeOn.scheduler)
            .doOnSubscribe(loadingOn())
            .doOnSuccess(loadingOff())
            .doOnError(loadingOff())
    }

    fun fetch(page: Int): Single<List<Tag>> {
        return useCase.get(page)
            .subscribeOn(subscribeOn.scheduler)
            .observeOn(observeOn.scheduler)
            .doOnSubscribe(loadingOn())
            .doOnSuccess(loadingOff())
            .doOnError(loadingOff())
    }
}