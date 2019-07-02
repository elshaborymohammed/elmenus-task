package com.elmenus.base.executor;

import io.reactivex.Scheduler;

/**
 * A WorkerThread provides a Scheduler to run a worker task in background thread
 */
public interface WorkerThread {
    Scheduler getScheduler();
}