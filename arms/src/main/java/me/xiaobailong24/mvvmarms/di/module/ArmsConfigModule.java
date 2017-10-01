package me.xiaobailong24.mvvmarms.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.xiaobailong24.mvvmarms.http.imageloader.BaseImageLoaderStrategy;
import me.xiaobailong24.mvvmarms.http.imageloader.glide.GlideImageLoaderStrategy;

/**
 * Created by xiaobailong24 on 2017/9/29.
 * 配置 Arms 框架里的组件，可以自由扩展
 */
@Module
public class ArmsConfigModule {
    private BaseImageLoaderStrategy mImageLoaderStrategy;


    @Singleton
    @Provides
    BaseImageLoaderStrategy provideImageLoaderStrategy() {
        //默认使用 Glide 加载图片
        return mImageLoaderStrategy == null ? new GlideImageLoaderStrategy() : mImageLoaderStrategy;
    }


    private ArmsConfigModule(Builder builder) {
        this.mImageLoaderStrategy = builder.imageLoaderStrategy;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private BaseImageLoaderStrategy imageLoaderStrategy;

        private Builder() {
        }

        public Builder imageLoaderStrategy(BaseImageLoaderStrategy imageLoaderStrategy) {
            this.imageLoaderStrategy = imageLoaderStrategy;
            return this;
        }

        public ArmsConfigModule build() {
            return new ArmsConfigModule(this);
        }
    }
}
