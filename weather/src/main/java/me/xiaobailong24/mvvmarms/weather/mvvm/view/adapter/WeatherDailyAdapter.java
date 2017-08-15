package me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import java.util.List;

import me.xiaobailong24.mvvmarms.mvvm.binding.BaseBindAdapter;
import me.xiaobailong24.mvvmarms.mvvm.binding.BaseBindHolder;
import me.xiaobailong24.mvvmarms.weather.BR;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherDailyResponse;

/**
 * Created by xiaobailong24 on 2017/8/15.
 * RecyclerView DataBinding Adapter
 */

public class WeatherDailyAdapter extends BaseBindAdapter<WeatherDailyResponse.DailyResult.Daily> {

    public WeatherDailyAdapter(@LayoutRes int layoutResId, @Nullable List<WeatherDailyResponse.DailyResult.Daily> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseBindHolder helper, WeatherDailyResponse.DailyResult.Daily item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.daily, item);
        binding.executePendingBindings();
    }

}
