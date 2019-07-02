package com.elmenus.task.data.requester

import com.elmenus.task.base.BaseTest
import com.elmenus.task.base.di.TestAppComponent
import com.elmenus.task.base.helper.TestHelper
import com.elmenus.task.data.remote.entity.Items
import com.elmenus.task.data.remote.requester.RequesterItemsApi
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import javax.inject.Inject

class ItemsApiTest : BaseTest() {

    @Inject
    lateinit var api: RequesterItemsApi

    private var subscriber = TestObserver<Items>()
    private lateinit var mockTags: Items

    override fun inject(testAppComponent: TestAppComponent) {
        testAppComponent.inject(this)
    }

    @Before
    fun setUp() {
        mockTags = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_ITEMS, Items::class.java)
    }

    @Test
    fun getSuccess() {
        mockHttpResponse(HttpURLConnection.HTTP_OK, TestHelper.MOCK_DATA_PATH_ITEMS)

        api.get("tagName")
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mockTags)
    }
}
