package com.elmenus.task.base.di

import com.elmenus.base.di.module.TestNetworkModule
import com.elmenus.base.di.module.TestSchedulerModule
import com.elmenus.task.data.repository.ItemsRepositoryTest
import com.elmenus.task.data.repository.TagsRepositoryTest
import com.elmenus.task.data.requester.ItemsApiTest
import com.elmenus.task.data.requester.TagApiTest
import dagger.Component
import javax.inject.Singleton

/**
 * this test component uses for injecting dependencies throughout test cases
 */
@Singleton
@Component(
    modules = [
        TestNetworkModule::class,
        TestAppModule::class,
        TestSchedulerModule::class
    ]
)
interface TestAppComponent {
    fun inject(testClass: TagApiTest)
    fun inject(testClass: ItemsApiTest)
    fun inject(testClass: TagsRepositoryTest)
    fun inject(testClass: ItemsRepositoryTest)
}