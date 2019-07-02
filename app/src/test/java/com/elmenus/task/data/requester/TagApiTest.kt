package com.elmenus.task.data.requester

import com.elmenus.task.base.BaseTest
import com.elmenus.task.base.di.TestAppComponent
import com.elmenus.task.base.helper.TestHelper
import com.elmenus.task.data.remote.entity.Tags
import com.elmenus.task.data.remote.requester.RequesterTagsApi
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection
import javax.inject.Inject

class TagApiTest : BaseTest() {

    @Inject
    lateinit var api: RequesterTagsApi

    private var subscriber = TestObserver<Tags>()
    private lateinit var mockTags: Tags


    override fun inject(testAppComponent: TestAppComponent) {
        testAppComponent.inject(this)
    }

    @Before
    fun setUp() {
        mockTags = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_TAGS, Tags::class.java)
    }

    @Test
    fun getSuccess() {
        mockHttpResponse(HttpURLConnection.HTTP_OK, TestHelper.MOCK_DATA_PATH_TAGS)

        api.get(0)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()
        subscriber.assertValue(mockTags)
    }

    @Test
    fun getPaging() {
        mockHttpResponse(HttpURLConnection.HTTP_OK, TestHelper.MOCK_DATA_PATH_TAGS)
        api.get(2)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.awaitTerminalEvent()

        subscriber.assertSubscribed()
        subscriber.assertNoErrors()
        subscriber.assertValue(mockTags)
    }
}
