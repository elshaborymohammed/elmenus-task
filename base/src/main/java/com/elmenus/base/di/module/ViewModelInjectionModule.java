package com.elmenus.base.di.module;

import androidx.lifecycle.ViewModelProvider;
import com.elmenus.base.app.AppViewModelProviderFactory;
import dagger.Binds;
import dagger.Module;

/**
 * this module ensure bindings object in ViewModel
 */
@Module
public abstract class ViewModelInjectionModule {

    @Binds
    abstract ViewModelProvider.Factory bind(AppViewModelProviderFactory factory);
}
