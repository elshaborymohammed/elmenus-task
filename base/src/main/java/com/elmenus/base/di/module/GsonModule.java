package com.elmenus.base.di.module;

import com.elmenus.base.di.qualifier.DatePattern;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class GsonModule {
    public static final String providesDatePattern() {
        return "yyyy-MM-dd'T'HH:mm:ssZ";
    }

    @Provides
    @Singleton
    Gson providesGson(@DatePattern String pattern) {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat(pattern)
                .create();
    }
}
