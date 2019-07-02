package com.elmenus.base.di.module;

import com.elmenus.base.di.qualifier.Endpoint;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

import javax.inject.Singleton;
import java.util.Set;

/**
 * Created by lshabory on 3/8/18.
 */
@Module
public class RequestBuilderModule {

    @Provides
    @Singleton
    Retrofit.Builder providesRequestBuilder(Set<Converter.Factory> converterFactories, Set<CallAdapter.Factory> callAdapterFactories, @Endpoint String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HttpUrl.parse(baseUrl));

        for (Converter.Factory factory : converterFactories) {
            builder.addConverterFactory(factory);
        }

        for (CallAdapter.Factory factory : callAdapterFactories) {
            builder.addCallAdapterFactory(factory);
        }
        return builder;
    }
}