package me.xiaobailong24.mvvmarms.lifecycle.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.xiaobailong24.mvvmarms.lifecycle.ConfigLifecycle;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * AndroidManifest.xml ManifestLifecycleParser
 */
@SuppressWarnings("all")
public final class ManifestLifecycleParser {
    private static final String MODULE_VALUE = "ConfigLifecycle";

    private final Context context;

    public ManifestLifecycleParser(Context context) {
        this.context = context;
    }

    public List<ConfigLifecycle> parse() {
        List<ConfigLifecycle> configLifecycles = new ArrayList<>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        Log.d("Lifecycle ---> ",
                                String.format("Find ConfigLifecycle in [%s]", key));
                        configLifecycles.add(parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse ConfigLifecycle", e);
        }

        return configLifecycles;
    }

    private static ConfigLifecycle parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find ConfigLifecycle implementation", e);
        }

        Object lifecycle;
        try {
            lifecycle = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate ConfigLifecycle implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate ConfigLifecycle implementation for " + clazz, e);
        }

        if (!(lifecycle instanceof ConfigLifecycle)) {
            throw new RuntimeException("Expected instanceof ConfigLifecycle, but found: " + lifecycle);
        }
        return (ConfigLifecycle) lifecycle;
    }
}