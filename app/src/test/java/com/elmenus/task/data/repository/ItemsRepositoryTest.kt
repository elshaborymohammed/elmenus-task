package com.elmenus.task.data.repository

import com.elmenus.base.api.ApiException
import com.elmenus.base.executor.WorkerThread
import com.elmenus.task.base.BaseTest
import com.elmenus.task.base.di.TestAppComponent
import com.elmenus.task.base.helper.TestHelper
import com.elmenus.task.data.local.dao.ItemsDao
import com.elmenus.task.data.local.entity.ItemRoom
import com.elmenus.task.data.remote.entity.Items
import com.elmenus.task.data.remote.requester.RequesterItemsApi
import com.elmenus.task.domain.model.Item
import com.google.gson.reflect.TypeToken
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection


@RunWith(MockitoJUnitRunner::class)
class ItemsRepositoryTest : BaseTest() {
    @Inject
    lateinit var api: RequesterItemsApi

    @Mock
    lateinit var dao: ItemsDao

    private lateinit var repository: ItemsRepository
    private var subscriber = TestObserver<List<Item>>()
    private lateinit var mockTags: List<Item>


    override fun inject(testAppComponent: TestAppComponent) {
        testAppComponent.inject(this)
    }

    @Before
    fun setUp() {
        mockTags = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_ITEMS, Items::class.java).data
    }

    @Test
    fun getFromDatabase() {
        setUpServerOK()
        setUpItemsDaoGET()
        repository = ItemsRepository(api, dao, WorkerThread { Schedulers.trampoline() })

        repository.get("tagName")
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mockTags)
    }

    @Test
    fun getFromApiSuccess() {
        setUpServerOK()
        setUpTagDaoGETEmpty()
        repository = ItemsRepository(api, dao, WorkerThread { Schedulers.trampoline() })

        repository.get("tagName")
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mockTags)
    }

    @Test
    fun getFromApiError() {
        setUpServerBAD()
        setUpTagDaoGETEmpty()
        repository = ItemsRepository(api, dao, WorkerThread { Schedulers.trampoline() })

        repository.get("tagName")
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(ApiException::class.java)
    }

    private fun setUpServerOK() {
        mockHttpResponse(HttpsURLConnection.HTTP_OK, TestHelper.MOCK_DATA_PATH_ITEMS)
    }

    private fun setUpServerBAD() {
        mockHttpResponse(HttpsURLConnection.HTTP_BAD_REQUEST, TestHelper.MOCK_DATA_PATH_ITEMS)
    }

    private fun setUpItemsDaoGET(): List<ItemRoom> {
        val listType = object : TypeToken<List<ItemRoom>>() {

        }.type
        val response: List<ItemRoom> = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_ITEMS_ROOM, listType)
        Mockito.`when`(dao.get("tagName")).thenReturn(response)
        return response
    }

    private fun setUpTagDaoGETEmpty(): List<ItemRoom> {
        val response: List<ItemRoom> = Collections.emptyList()
        Mockito.`when`(dao.get("tagName")).thenReturn(response)
        return response
    }
}