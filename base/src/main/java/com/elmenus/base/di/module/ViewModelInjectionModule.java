package com.elmenus.base.di.module;

import androidx.lifecycle.ViewModelProvider;
import com.elmenus.base.app.AppViewModelProviderFactory;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelInjectionModule {

    @Binds
    abstract ViewModelProvider.Factory bind(AppViewModelProviderFactory factory);
}
