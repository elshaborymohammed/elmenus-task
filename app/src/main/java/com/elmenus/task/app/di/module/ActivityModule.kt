package com.elmenus.task.app.di.module

import com.elmenus.task.presentation.items.ItemDetailsActivity
import com.elmenus.task.presentation.home.HomeActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * provides activities for inject objects into it
 */
@Module(includes = [AndroidInjectionModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindListItemActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun bindItemDetailsActivity(): ItemDetailsActivity
}