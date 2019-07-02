package com.elmenus.task.presentation.viewmodel.tag

import com.elmenus.task.base.helper.TestHelper
import com.elmenus.task.data.remote.entity.Tags
import com.elmenus.task.domain.model.Tag
import com.elmenus.task.presentation.tag.TagViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.Relay
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
class TagViewModelTest {

    @Mock
    lateinit var viewModel: TagViewModel

    private val loading: Relay<Boolean> = BehaviorRelay.create()
    private var subscriber = TestObserver<List<Tag>>()

    @Before
    fun setUp() {
        Mockito.`when`(viewModel.loading()).thenReturn(loading)
    }

    @Test
    fun getSuccess() {
        val mock = setUpGetSuccess()
        viewModel.get().subscribe(subscriber)
        viewModel.loading().subscribe(loading)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mock)
    }

    @Test
    fun getError() {
        val mock = setUpGetError()
        viewModel.get().subscribe(subscriber)
        viewModel.loading().subscribe(loading)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(mock)
    }

    @Test
    fun fetchSuccess() {
        val mock = setUpFetchSuccess()
        viewModel.fetch(1).subscribe(subscriber)
        viewModel.loading().subscribe(loading)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mock)
    }

    @Test
    fun fetchError() {
        val mock = setUpFetchError()
        viewModel.fetch(1).subscribe(subscriber)
        viewModel.loading().subscribe(loading)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(mock)
    }

    @Test
    fun loading() {
        val test = viewModel.loading().test()
        viewModel.loading().accept(true)
        viewModel.loading().accept(false)

        test.assertSubscribed()
        test.assertValues(true, false)
    }

    private fun setUpGetSuccess(): List<Tag> {
        val response = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_TAGS, Tags::class.java).data
        Mockito.`when`(viewModel.get()).thenReturn(Single.just(response))
        return response
    }

    private fun setUpGetError(): Throwable {
        val error = IOException()
        Mockito.`when`(viewModel.get()).thenReturn(Single.error(error))
        return error
    }

    private fun setUpFetchSuccess(): List<Tag> {
        val response = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_TAGS, Tags::class.java).data
        Mockito.`when`(viewModel.fetch(1)).thenReturn(Single.just(response))
        return response
    }

    private fun setUpFetchError(): Throwable {
        val error = IOException()
        Mockito.`when`(viewModel.fetch(1)).thenReturn(Single.error(error))
        return error
    }
}