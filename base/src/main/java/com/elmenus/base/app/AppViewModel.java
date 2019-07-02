package com.elmenus.base.app;

import androidx.lifecycle.ViewModel;
import com.jakewharton.rxrelay2.Relay;
import com.jakewharton.rxrelay2.ReplayRelay;
import io.reactivex.functions.Consumer;

public abstract class AppViewModel extends ViewModel {
    private ReplayRelay<Boolean> loading = ReplayRelay.create();

    public final Relay<Boolean> loading() {
        return loading;
    }

//    protected final void loadingOn() {
//        loading.accept(Boolean.TRUE);
//    }

//    protected final void loadingOff() {
//        loading.accept(Boolean.FALSE);
//    }

    public Consumer loadingOn() {
        return disposable -> loading().accept(Boolean.TRUE);
    }

    public Consumer loadingOff() {
        return response -> loading().accept(Boolean.FALSE);
    }

//    public Consumer<Throwable> doOnError() {
//        return throwable -> loading().accept(Boolean.FALSE);
//    }
}
