package me.xiaobailong24.mvvmarms.http.imageloader.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.xiaobailong24.mvvmarms.http.imageloader.BaseImageLoaderStrategy;
import me.xiaobailong24.mvvmarms.repository.utils.Preconditions;
import timber.log.Timber;

/**
 * @author xiaobailong24
 * @date 2017/8/17
 * Glide 图片默认加载策略
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<ImageConfigImpl>, GlideAppliesOptions {

    @Override
    public void loadImage(Context context, ImageConfigImpl config) {
        Preconditions.checkNotNull(context, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");
        Preconditions.checkNotNull(config.getImageView(), "ImageView is required");

        GlideRequests requests = GlideArms.with(context);

        GlideRequest<Drawable> glideRequest = requests.load(config.getUrl())
                //动画
                .transition(DrawableTransitionOptions.withCrossFade())
                //适应居中
                .fitCenter();

        //缓存策略
        switch (config.getCacheStrategy()) {
            case 0:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                break;
        }

        //自定义改变图片形状
        if (config.getTransformation() != null) {
            glideRequest.transform(config.getTransformation());
        }

        //设置占位符
        if (config.getPlaceholder() != 0) {
            glideRequest.placeholder(config.getPlaceholder());
        }

        //设置错误图片
        if (config.getErrorPic() != 0) {
            glideRequest.error(config.getErrorPic());
        }

        //设置 url 为 null 是图片
        if (config.getFallback() != 0) {
            glideRequest.fallback(config.getFallback());
        }

        glideRequest.into(config.getImageView());
    }

    @Override
    public void clear(final Context context, ImageConfigImpl config) {
        Preconditions.checkNotNull(context, "Context is required");
        Preconditions.checkNotNull(config, "ImageConfigImpl is required");
        // Although it’s good practice to clear loads you no longer need, you’re not required to do so.
        // In fact, Glide will automatically clear the load and recycle any resources used by the load
        // when the Activity or Fragment you pass in to Glide.with() is destroyed.
        if (config.getImageViews() != null && config.getImageViews().length > 0) {
            //取消正在进行的加载任务，并释放资源
            for (ImageView imageView : config.getImageViews()) {
                GlideArms.get(context).getRequestManagerRetriever().get(context).clear(imageView);
            }
        }

        if (config.isClearDiskCache()) {
            //清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            GlideArms.get(context).clearDiskCache();
                        }
                    });
        }

        if (config.isClearMemory()) {
            //清除内存缓存
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            GlideArms.get(context).clearMemory();
                        }
                    });
        }
    }

    @Override
    public void applyGlideOptions(Context context, GlideBuilder builder) {
        Timber.w("applyGlideOptions");
    }
}
