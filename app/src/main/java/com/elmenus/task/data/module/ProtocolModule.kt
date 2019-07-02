package com.elmenus.task.data.module

import com.elmenus.task.data.repository.ItemsRepository
import com.elmenus.task.data.repository.TagsRepository
import com.elmenus.task.domain.protocol.IItemsProtocol
import com.elmenus.task.domain.protocol.ITagsProtocol
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * this is injection module inject Repositories into dagger2 graph
 * */
@Module
class ProtocolModule {

    @Provides
    @Singleton
    fun providesTagsProtocol(repository: TagsRepository): ITagsProtocol {
        return repository
    }

    @Provides
    @Singleton
    fun providesItemsProtocol(repository: ItemsRepository): IItemsProtocol {
        return repository
    }
}