package com.elmenus.base.executor;

import io.reactivex.Scheduler;

public interface WorkerThread {
    Scheduler getScheduler();
}