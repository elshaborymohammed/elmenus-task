package com.elmenus.task.app.di

import android.app.Application
import com.elmenus.base.di.module.AuthenticatorModule
import com.elmenus.base.di.module.NetworkModule
import com.elmenus.task.app.App
import com.elmenus.task.app.di.module.ActivityModule
import com.elmenus.task.app.di.module.AppModule
import com.elmenus.task.app.di.module.FragmentModule
import com.elmenus.task.app.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Build Component that provides objects injection based on Modules
 */
@Singleton
@Component(
    modules = [
        NetworkModule::class,
        AuthenticatorModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}