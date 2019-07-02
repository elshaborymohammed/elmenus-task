package com.elmenus.task.domain.usecase

import com.elmenus.task.base.helper.TestHelper
import com.elmenus.task.data.remote.entity.Items
import com.elmenus.task.domain.model.Item
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
class ItemsUseCaseTest {

    @Mock
    lateinit var useCase: ItemsUseCase

    private var subscriber = TestObserver<List<Item>>()
    private var subscriberItem = TestObserver<Item>()

    @Test
    fun getSuccess() {
        val mock = setUpGetSuccess()
        useCase.get("tagName").subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mock)
    }

    @Test
    fun getError() {
        val mock = setUpGetError()
        useCase.get("tagName").subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(mock)
    }

    @Test
    fun getOneSuccess() {
        val mock = setUpGetOneSuccess()
        useCase.getOne("ProductName").subscribe(subscriberItem)

        subscriberItem.assertSubscribed()
        subscriberItem.awaitTerminalEvent()
        subscriberItem.assertNoErrors()
        subscriberItem.assertValue(mock)
    }

    @Test
    fun getOneError() {
        val mock = setUpGetOneError()
        useCase.getOne("ProductName").subscribe(subscriberItem)

        subscriberItem.assertSubscribed()
        subscriberItem.awaitTerminalEvent()
        subscriberItem.assertError(mock)
    }

    private fun setUpGetSuccess(): List<Item> {
        val response = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_ITEMS, Items::class.java).data
        Mockito.`when`(useCase.get("tagName")).thenReturn(Single.just(response))
        return response
    }

    private fun setUpGetError(): Throwable {
        val error = IOException()
        Mockito.`when`(useCase.get("tagName")).thenReturn(Single.error(error))
        return error
    }

    private fun setUpGetOneSuccess(): Item {
        val response = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_ITEMS, Items::class.java).data[0]
        Mockito.`when`(useCase.getOne("ProductName")).thenReturn(Single.just(response))
        return response
    }

    private fun setUpGetOneError(): Throwable {
        val error = IOException()
        Mockito.`when`(useCase.getOne("ProductName")).thenReturn(Single.error(error))
        return error
    }
}