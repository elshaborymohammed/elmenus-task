package com.elmenus.task.app.di.module

import androidx.lifecycle.ViewModel
import com.elmenus.base.di.key.ViewModelKey
import com.elmenus.base.di.module.ViewModelInjectionModule
import com.elmenus.task.presentation.items.ItemViewModel
import com.elmenus.task.presentation.tag.TagViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * provides ViewModel for inject objects into it
 */
@Module(includes = [ViewModelInjectionModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TagViewModel::class)
    abstract fun bindTagViewModel(viewModel: TagViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ItemViewModel::class)
    abstract fun bindItemViewModel(viewModel: ItemViewModel): ViewModel
}