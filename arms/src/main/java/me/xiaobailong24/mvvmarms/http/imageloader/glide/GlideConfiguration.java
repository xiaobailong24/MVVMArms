package me.xiaobailong24.mvvmarms.http.imageloader.glide;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;
import java.io.InputStream;

import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;
import me.xiaobailong24.mvvmarms.http.OkHttpUrlLoader;
import me.xiaobailong24.mvvmarms.http.imageloader.BaseImageLoaderStrategy;
import me.xiaobailong24.mvvmarms.utils.ArmsUtils;
import me.xiaobailong24.mvvmarms.utils.DataHelper;

/**
 * Created by xiaobailong24 on 2017/8/17.
 * Glide 配置
 *
 * @link http://bumptech.github.io/glide/doc/configuration.html
 */
@GlideModule(glideName = "GlideArms")
public class GlideConfiguration extends AppGlideModule {
    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024;//图片缓存文件最大值为 100Mb

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
        ArmsComponent armsComponent = ArmsUtils.INSTANCE.obtainArmsComponent(context);
        builder.setDiskCache(new DiskCache.Factory() {
            @Nullable
            @Override
            public DiskCache build() {
                // Careful: the external cache directory doesn't enforce permissions
                return DiskLruCacheWrapper.get(DataHelper.makeDirs(new File(armsComponent.cacheFile(), "Glide")), IMAGE_DISK_CACHE_MAX_SIZE);
            }
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        //将配置 Glide 的机会转交给 GlideImageLoaderStrategy,如你觉得框架提供的 GlideImageLoaderStrategy
        //并不能满足自己的需求,想自定义 BaseImageLoaderStrategy,那请你最好实现 GlideAppliesOptions
        //因为只有成为 GlideAppliesOptions 的实现类,这里才能调用 applyGlideOptions(),让你具有配置 Glide 的权利

        BaseImageLoaderStrategy imageLoaderStrategy = armsComponent.imageLoader().getStrategy();
        if (imageLoaderStrategy instanceof GlideAppliesOptions) {
            ((GlideAppliesOptions) imageLoaderStrategy).applyGlideOptions(context, builder);
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        //Glide默认使用HttpURLConnection做网络请求,在这切换成okhttp请求
        ArmsComponent armsComponent = ArmsUtils.INSTANCE.obtainArmsComponent(context);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(armsComponent.okHttpClient()));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
