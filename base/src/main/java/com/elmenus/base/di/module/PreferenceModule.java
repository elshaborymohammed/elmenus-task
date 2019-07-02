package com.elmenus.base.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.elmenus.base.di.qualifier.ApplicationContext;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * Created by lshabory on 3/16/18.
 */

@Module
public class PreferenceModule {
    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}