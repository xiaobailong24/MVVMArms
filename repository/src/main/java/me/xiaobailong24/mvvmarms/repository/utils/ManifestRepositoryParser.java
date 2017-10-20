package me.xiaobailong24.mvvmarms.repository.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.xiaobailong24.mvvmarms.repository.ConfigRepository;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * AndroidManifest.xml ManifestRepositoryParser
 */
public final class ManifestRepositoryParser {
    private static final String MODULE_VALUE = "ConfigRepository";

    private final Context context;

    public ManifestRepositoryParser(Context context) {
        this.context = context;
    }

    public List<ConfigRepository> parse() {
        List<ConfigRepository> configRepositories = new ArrayList<>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        Log.d("Repository ---> ",
                                String.format("Find ConfigRepository in [%s]", key));
                        configRepositories.add(parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse ConfigRepository", e);
        }

        return configRepositories;
    }

    private static ConfigRepository parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find ConfigRepository implementation", e);
        }

        Object repository;
        try {
            repository = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate ConfigRepository implementation for " + clazz, e);
        }

        if (!(repository instanceof ConfigRepository)) {
            throw new RuntimeException("Expected instanceof ConfigRepository, but found: " + repository);
        }
        return (ConfigRepository) repository;
    }
}