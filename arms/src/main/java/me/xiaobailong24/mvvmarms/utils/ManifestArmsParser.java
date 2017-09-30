package me.xiaobailong24.mvvmarms.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.xiaobailong24.mvvmarms.base.ConfigArms;

/**
 * Created by xiaobailong24 on 2017/6/16.
 * AndroidManifest.xml ManifestArmsParser
 */
@SuppressWarnings("all")
public final class ManifestArmsParser {
    private static final String MODULE_VALUE = "ConfigArms";

    private final Context context;

    public ManifestArmsParser(Context context) {
        this.context = context;
    }

    public List<ConfigArms> parse() {
        List<ConfigArms> armses = new ArrayList<>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        Log.d("ManifestArmsParser ---> ",
                                String.format("Find ConfigArms in [%s]", key));
                        armses.add(parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse ConfigArms", e);
        }

        return armses;
    }

    private static ConfigArms parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find ConfigArms implementation", e);
        }

        Object arms;
        try {
            arms = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate ConfigArms implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate ConfigArms implementation for " + clazz, e);
        }

        if (!(arms instanceof ConfigArms)) {
            throw new RuntimeException("Expected instanceof ConfigArms, but found: " + arms);
        }
        return (ConfigArms) arms;
    }
}