package com.elmenus.base.di.module;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lshabory on 3/8/18.
 */

@Module
public class TestNetworkModule {

    @Provides
    @IntoSet
    Protocol providesProtocolHTTP1() {
        return Protocol.HTTP_1_1;
    }

    @Provides
    @IntoSet
    Protocol providesProtocolHTTP2() {
        return Protocol.HTTP_2;
    }

    @Provides
    @IntoSet
    Interceptor providesBodyInterceptors() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClientCached(Set<Protocol> protocols, Set<Interceptor> interceptors, Authenticator authenticator) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .protocols(new ArrayList<>(protocols))
                .connectTimeout(3 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS);
        builder.interceptors().addAll(interceptors);

        if (Authenticator.NONE != authenticator)
            builder.authenticator(authenticator);
        return builder;
    }
}