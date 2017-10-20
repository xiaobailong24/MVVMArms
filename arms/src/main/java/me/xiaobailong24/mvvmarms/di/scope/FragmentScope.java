package me.xiaobailong24.mvvmarms.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Dagger FragmentScope
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
