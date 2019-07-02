package com.elmenus.base.di.module;

import android.app.Application;
import android.content.Context;
import com.elmenus.base.di.qualifier.ApplicationContext;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by lshabory on 3/8/18.
 */

@Module
public class ContextModule {

    @Provides
    @Singleton
    @ApplicationContext
    Context providesApplicationContext(Application application) {
        return application.getApplicationContext();
    }
}