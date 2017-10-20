package me.xiaobailong24.mvvmarms.http.imageloader.glide;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import me.xiaobailong24.mvvmarms.http.imageloader.BaseImageConfig;

/**
 * @author xiaobailong24
 * @date 2017/8/17
 * 这里存放图片请求的配置信息,可以一直扩展字段,如果外部调用时想让图片加载框架
 * 做一些操作,比如清除缓存或者切换缓存策略,则可以定义一个 int 类型的变量,内部根据 switch(int) 做不同的操作
 * 其他操作同理
 */
public class ImageConfigImpl extends BaseImageConfig {
    /**
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#ALL : 0
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#NONE : 1
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#DATA : 2
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#RESOURCE : 3
     * @see com.bumptech.glide.load.engine.DiskCacheStrategy#AUTOMATIC : 4
     */
    /**
     * 缓存策略
     */
    private int cacheStrategy;
    /**
     * Fallback Drawables are shown when the requested url/model is null
     */
    private int fallback;
    /**
     * 改变图形形状
     */
    private BitmapTransformation transformation;
    private ImageView[] imageViews;
    /**
     * 清除内存缓存
     */
    private boolean isClearMemory;
    /**
     * 清除本地缓存
     */
    private boolean isClearDiskCache;

    public ImageConfigImpl(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.fallback = builder.fallback;
        this.cacheStrategy = builder.cacheStrategy;
        this.transformation = builder.transformation;
        this.imageViews = builder.imageViews;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
    }

    public int getCacheStrategy() {
        return cacheStrategy;
    }

    public int getFallback() {
        return fallback;
    }

    public BitmapTransformation getTransformation() {
        return transformation;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        /**
         * 图片 Url
         */
        private String url;
        /**
         * 要显示的 ImageView
         */
        private ImageView imageView;
        /**
         * 图片占位符
         */
        private int placeholder;
        /**
         * 错误图片占位符
         */
        private int errorPic;
        /**
         * Fallback Drawables are shown when the requested url/model is null
         */
        private int fallback;
        /**
         * 缓存策略
         */
        private int cacheStrategy;
        /**
         * 改变图形形状
         */
        private BitmapTransformation transformation;
        private ImageView[] imageViews;
        /**
         * 清除内存缓存
         */
        private boolean isClearMemory;
        /**
         * 清除本地缓存
         */
        private boolean isClearDiskCache;

        private Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public Builder fallback(int fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder cacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Builder transformation(BitmapTransformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder imageViews(ImageView... imageViews) {
            this.imageViews = imageViews;
            return this;
        }

        public Builder isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Builder isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }

        public ImageConfigImpl build() {
            return new ImageConfigImpl(this);
        }


    }
}
