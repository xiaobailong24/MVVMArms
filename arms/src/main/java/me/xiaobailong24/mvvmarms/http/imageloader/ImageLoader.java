package me.xiaobailong24.mvvmarms.http.imageloader;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author xiaobailong24
 * @date 2017/8/17
 * 使用策略模式和建造者模式,可以动态切换图片请求框架(比如说切换成 Picasso )
 * 当需要切换图片请求框架或图片请求框架升级后变更了 Api 时
 * 这里可以将影响范围降到最低,所以封装 {@link ImageLoader} 是为了屏蔽这个风险
 */
@Singleton
public final class ImageLoader {
    private BaseImageLoaderStrategy mStrategy;

    @Inject
    public ImageLoader(BaseImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
    }


    /**
     * 加载图片
     *
     * @param context Context
     * @param config  ImageConfig
     * @param <T>     ImageConfig
     */
    public <T extends BaseImageConfig> void loadImage(Context context, T config) {
        mStrategy.loadImage(context, config);
    }

    /**
     * 停止加载或清理缓存
     *
     * @param context Context
     * @param config  ImageConfig
     * @param <T>     ImageConfig
     */
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
