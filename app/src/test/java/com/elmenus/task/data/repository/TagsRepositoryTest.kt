package com.elmenus.task.data.repository

import com.elmenus.base.api.ApiException
import com.elmenus.base.executor.WorkerThread
import com.elmenus.task.base.BaseTest
import com.elmenus.task.base.di.TestAppComponent
import com.elmenus.task.base.helper.TestHelper
import com.elmenus.task.data.local.dao.TagsDao
import com.elmenus.task.data.local.entity.TagRoom
import com.elmenus.task.data.remote.entity.Tags
import com.elmenus.task.data.remote.requester.RequesterTagsApi
import com.elmenus.task.domain.model.Tag
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
class TagsRepositoryTest : BaseTest() {
    @Inject
    lateinit var api: RequesterTagsApi

    @Mock
    lateinit var dao: TagsDao

    private lateinit var repository: TagsRepository
    private var subscriber = TestObserver<List<Tag>>()
    private lateinit var mockTags: List<Tag>


    override fun inject(testAppComponent: TestAppComponent) {
        testAppComponent.inject(this)
    }

    @Before
    fun setUp() {
        mockTags = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_TAGS, Tags::class.java).data
    }

    @Test
    fun getFromDatabase() {
        setUpServerOK()
        setUpTagDaoGET()
        repository = TagsRepository(api, dao, WorkerThread { Schedulers.trampoline() })

        repository.get()
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
        repository = TagsRepository(api, dao, WorkerThread { Schedulers.trampoline() })

        repository.get()
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
        repository = TagsRepository(api, dao, WorkerThread { Schedulers.trampoline() })

        repository.get()
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.awaitTerminalEvent()
        subscriber.assertError(ApiException::class.java)
    }

    private fun setUpServerOK() {
        mockHttpResponse(HttpsURLConnection.HTTP_OK, TestHelper.MOCK_DATA_PATH_TAGS)
    }

    private fun setUpServerBAD() {
        mockHttpResponse(HttpsURLConnection.HTTP_BAD_REQUEST, TestHelper.MOCK_DATA_PATH_TAGS)
    }

    private fun setUpTagDaoGET(): List<TagRoom> {
        val listType = object : TypeToken<List<TagRoom>>() {

        }.type
        val response: List<TagRoom> = TestHelper.loadJson(TestHelper.MOCK_DATA_PATH_TAGS_ROOM, listType)
        Mockito.`when`(dao.get()).thenReturn(response)
        return response
    }

    private fun setUpTagDaoGETEmpty(): List<TagRoom> {
        val response: List<TagRoom> = Collections.emptyList()
        Mockito.`when`(dao.get()).thenReturn(response)
        return response
    }
}