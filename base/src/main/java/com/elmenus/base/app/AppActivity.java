package com.elmenus.base.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.elmenus.base.R;
import com.elmenus.base.api.ApiException;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.io.IOException;

/**
 * Created by lshabory on 3/8/18.
 */

public abstract class AppActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private final CompositeDisposable disposable = new CompositeDisposable();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        if (layoutRes() > -1)
            setContentView(layoutRes());
        onCreate();
        disposable.addAll(subscriptions());
    }

    @LayoutRes
    protected abstract int layoutRes();

    protected abstract void onCreate();

    protected Disposable[] subscriptions() {
        return new Disposable[0];
    }

    protected void subscribe(Disposable d) {
        disposable.add(d);
    }

    @Override
    protected void onDestroy() {
        disposable.dispose();
        disposable.clear();
        super.onDestroy();
    }

    protected Consumer<Throwable> onError() {
        return it -> {
            it.printStackTrace();
            if (it instanceof ApiException) {
                Toast.makeText(this, "Error in server.....", Toast.LENGTH_LONG).show();
            } else if (it instanceof IOException) {
                Toast.makeText(this, getString(R.string.connection_lost), Toast.LENGTH_LONG).show();
            }
        };
    }
}