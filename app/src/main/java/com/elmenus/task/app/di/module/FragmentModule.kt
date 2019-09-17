package com.elmenus.task.app.di.module

import com.elmenus.task.presentation.items.ItemDetailsFragment
import com.elmenus.task.presentation.items.ItemsFragment
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * provides activities for inject objects into it
 */
@Module(includes = [AndroidInjectionModule::class])
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindListItemFragment(): ItemsFragment

    @ContributesAndroidInjector
    abstract fun bindItemDetailsFragment(): ItemDetailsFragment
}