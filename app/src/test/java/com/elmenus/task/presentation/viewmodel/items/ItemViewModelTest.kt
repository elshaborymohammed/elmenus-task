package com.elmenus.task.presentation.viewmodel.items

import com.elmenus.task.base.helper.TestHelper
import com.elmenus.task.data.remote.entity.Items
import com.elmenus.task.domain.model.Item
import com.elmenus.task.presentation.items.ItemViewModel
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
class ItemViewModelTest {

    @Mock
    lateinit var viewModel: ItemViewModel

    private val loading: Relay<Boolean> = BehaviorRelay.create()
    private var subscriber = TestObserver<List<Item>>()
    private var subscriberItem = TestObserver<Item>()

    @Before
    fun setUp() {
        Mockito.`when`(viewModel.loading()).thenReturn(loading)
    }

    @Test
    fun getSuccess() {
        val mock = setUpGetSuccess()
        viewModel.get("tagName").subscribe(subscriber)
        viewModel.loading().subscribe(loading)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mock)
    }

    @Test
    fun getError() {
        val mock = setUpGetError()
        viewModel.get("tagName").subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(mock)
    }

    @Test
    fun getOneSuccess() {
        val mock = setUpGetOneSuccess()
        viewModel.getOne("ProductName").subscribe(subscriberItem)

        subscriberItem.assertSubscribed()
        subscriberItem.awaitTerminalEvent()
        subscriberItem.assertNoErrors()
        subscriberItem.assertValue(mock)
    }

    @Test
    fun getOneError() {
        val mock = setUpGetOneError()
        viewModel.getOne("ProductName").subscribe(subscriberItem)
        viewModel.loading().subscribe(loading)

        subscriberItem.assertSubscribed()
        subscriberItem.awaitTerminalEvent()
        subscriberItem.assertError(mock)
    }

    @Test
    fun loading() {
        val test = viewModel.loading().test()
        viewModel.loading().accept(true)
        viewModel.loading().accept(false)

        test.assertSubscribed()
        test.assertValues(true, false)
    }

    private fun setUpGetSuccess(): List<Item> {
        val response = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_ITEMS, Items::class.java).data
        Mockito.`when`(viewModel.get("tagName")).thenReturn(Single.just(response))
        return response
    }

    private fun setUpGetError(): Throwable {
        val error = IOException()
        Mockito.`when`(viewModel.get("tagName")).thenReturn(Single.error(error))
        return error
    }

    private fun setUpGetOneSuccess(): Item {
        val response = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_ITEMS, Items::class.java).data[0]
        Mockito.`when`(viewModel.getOne("ProductName")).thenReturn(Single.just(response))
        return response
    }

    private fun setUpGetOneError(): Throwable {
        val error = IOException()
        Mockito.`when`(viewModel.getOne("ProductName")).thenReturn(Single.error(error))
        return error
    }
}