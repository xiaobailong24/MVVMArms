package me.xiaobailong24.mvvmarms.http.imageloader;

import android.content.Context;

import javax.inject.Inject;

import me.xiaobailong24.mvvmarms.di.scope.ArmsScope;

/**
 * Created by xiaobailong24 on 2017/8/17.
 * 策略模式，封装类，持有 BaseImageLoaderStrategy 的引用
 */
@ArmsScope
public final class ImageLoader {
    private BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader(BaseImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
    }


    public <T extends BaseImageConfig> void loadImage(Context context, T config) {
        mStrategy.loadImage(context, config);
    }

    public <T extends BaseImageConfig> void clear(Context context, T config) {
        mStrategy.clear(context, config);
    }

    public BaseImageLoaderStrategy getStrategy() {
        return mStrategy;
    }

    public void setStrategy(BaseImageLoaderStrategy strategy) {
        mStrategy = strategy;
    }
}
