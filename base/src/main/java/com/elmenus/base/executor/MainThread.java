package com.elmenus.base.executor;

import io.reactivex.Scheduler;

/**
 * A MainThread provides a Scheduler to run on UIThread
 */
public interface MainThread {
    Scheduler getScheduler();
}