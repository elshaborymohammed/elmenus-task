package com.elmenus.base.di.module;

import com.elmenus.base.executor.MainThread;
import com.elmenus.base.executor.WorkerThread;
import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Singleton;

/**
 * Created by lshabory on 3/16/18.
 */

@Module
public class SchedulerModule {
    @Provides
    @Singleton
    MainThread providesMainThread() {
        return () -> AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    WorkerThread providesNetworkScheduler() {
        return () -> Schedulers.newThread();
    }
}