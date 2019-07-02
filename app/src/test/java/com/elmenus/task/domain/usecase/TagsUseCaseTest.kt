package com.elmenus.task.domain.usecase

import com.elmenus.task.base.helper.TestHelper
import com.elmenus.task.data.remote.entity.Tags
import com.elmenus.task.domain.model.Tag
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class TagsUseCaseTest {

    @Mock
    lateinit var useCase: TagsUseCase

    private var subscriber = TestObserver<List<Tag>>()

    @Test
    fun getSuccess() {
        val mock = setUpGetSuccess()
        useCase.get().subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mock)
    }

    @Test
    fun getError() {
        val mock = setUpGetError()
        useCase.get().subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(mock)
    }

    @Test
    fun getByPageSuccess() {
        val mock = setUpFetchSuccess()
        useCase.get(1).subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mock)
    }

    @Test
    fun getByPageError() {
        val mock = setUpFetchError()
        useCase.get(1).subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(mock)
    }

    private fun setUpGetSuccess(): List<Tag> {
        val response = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_TAGS, Tags::class.java).data
        Mockito.`when`(useCase.get()).thenReturn(Single.just(response))
        return response
    }

    private fun setUpGetError(): Throwable {
        val error = IOException()
        Mockito.`when`(useCase.get()).thenReturn(Single.error(error))
        return error
    }

    private fun setUpFetchSuccess(): List<Tag> {
        val response = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_TAGS, Tags::class.java).data
        Mockito.`when`(useCase.get(1)).thenReturn(Single.just(response))
        return response
    }

    private fun setUpFetchError(): Throwable {
        val error = IOException()
        Mockito.`when`(useCase.get(1)).thenReturn(Single.error(error))
        return error
    }
}