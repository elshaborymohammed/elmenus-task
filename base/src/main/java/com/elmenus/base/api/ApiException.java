package com.elmenus.base.api;


import androidx.annotation.Nullable;
import com.google.gson.Gson;

public class ApiException extends Exception {
    private final int code;
    @Nullable
    private final String error;

    public ApiException(int code, @Nullable String error) {
        super();
        this.code = code;
        this.error = error;
    }

    public static ApiException create(Throwable throwable) {
        if (throwable instanceof ApiException)
            return (ApiException) throwable;
        return null;
    }

    public int code() {
        return code;
    }

    @Nullable
    public String error() {
        return error;
    }

    @Nullable
    public <T> T error(Class<T> classOfT) {
        return new Gson().fromJson(error, classOfT);
    }
}
