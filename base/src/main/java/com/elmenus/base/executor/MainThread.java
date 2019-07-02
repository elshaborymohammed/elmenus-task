package com.elmenus.base.executor;

import io.reactivex.Scheduler;

public interface MainThread {
    Scheduler getScheduler();
}