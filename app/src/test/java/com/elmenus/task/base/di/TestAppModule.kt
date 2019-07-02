package com.elmenus.task.base.di

import com.elmenus.base.api.RxCallAdapter
import com.elmenus.base.di.module.GsonModule
import com.elmenus.base.di.module.RequestModule
import com.elmenus.base.di.qualifier.DatePattern
import com.elmenus.base.di.qualifier.Endpoint
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.mockwebserver.MockWebServer
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        GsonModule::class,
        RequestModule::class
    ]
)
class TestAppModule {

    @Provides
    @Singleton
    @DatePattern
    fun providesDatePattern(): String {
        return GsonModule.providesDatePattern()
    }

    @Provides
    @Singleton
    @Endpoint
    fun providesEndpoint(mockWebServer: MockWebServer): String {
        return mockWebServer.url("/").toString()
    }

    @Provides
    @IntoSet
    internal fun providesGsonConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @IntoSet
    fun providesCompactCallAdapterFactory(): CallAdapter.Factory {
        return RxCallAdapter.Factory.create()
    }

    @Singleton
    @Provides
    fun providesMockWebServer(): MockWebServer = MockWebServer()

    @Provides
    @Singleton
    fun providesAuthenticator(): Authenticator {
        return object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                println("TestAppModule.authenticate")
                return null
            }
        }
    }
}