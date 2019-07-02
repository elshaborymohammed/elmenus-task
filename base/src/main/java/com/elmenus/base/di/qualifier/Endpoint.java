package com.elmenus.base.di.qualifier;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lshabory on 2/12/2018.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Endpoint {
}