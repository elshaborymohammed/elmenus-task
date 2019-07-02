package com.elmenus.base.di.module;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;

import javax.inject.Singleton;

@Module
public class AuthenticatorModule {
    @Provides
    @Singleton
    Authenticator providesAuthenticator() {
        return Authenticator.NONE;
    }
}