package me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.allen.library.SuperTextView;
import com.bumptech.glide.load.resource.bitmap.FitCenter;

import java.util.List;
import java.util.Locale;

import me.xiaobailong24.mvvmarms.di.component.ArmsComponent;
import me.xiaobailong24.mvvmarms.http.imageloader.glide.ImageConfigImpl;
import me.xiaobailong24.mvvmarms.mvvm.binding.BaseBindAdapter;
import me.xiaobailong24.mvvmarms.mvvm.binding.BaseBindHolder;
import me.xiaobailong24.mvvmarms.utils.ArmsUtils;
import me.xiaobailong24.mvvmarms.weather.BR;
import me.xiaobailong24.mvvmarms.weather.R;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.Api;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherDailyResponse;

/**
 * Created by xiaobailong24 on 2017/8/15.
 * RecyclerView DataBinding Adapter
 */

public class WeatherDailyAdapter extends BaseBindAdapter<WeatherDailyResponse.DailyResult.Daily> {

    private ArmsComponent mArmsComponent;//获得到 ArmsComponent，就能获得 Arms 里的一切

    public WeatherDailyAdapter(@LayoutRes int layoutResId, @Nullable List<WeatherDailyResponse.DailyResult.Daily> data) {
        super(layoutResId, data);
    }

    @Override
    public BaseBindHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseBindHolder holder = super.onCreateViewHolder(parent, viewType);
        mArmsComponent = ArmsUtils.INSTANCE.obtainArmsComponent(mContext);
        return holder;
    }

    @Override
    protected void convert(BaseBindHolder helper, WeatherDailyResponse.DailyResult.Daily item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.daily, item);
        binding.executePendingBindings();

        SuperTextView superTextView = helper.getView(R.id.super_item_daily);
        mArmsComponent.imageLoader()
                .loadImage(mContext,
                        ImageConfigImpl.builder()
                                .url(String.format(Locale.CHINESE, Api.API_WEATHER_ICON_URL, item.getCodeDay()))
                                .placeholder(R.mipmap.ic_placeholder)
                                .errorPic(R.mipmap.weather_unknown)
                                .transformation(new FitCenter())
                                .imageView(superTextView.getRightIconIV())
                                .build());
    }

}
