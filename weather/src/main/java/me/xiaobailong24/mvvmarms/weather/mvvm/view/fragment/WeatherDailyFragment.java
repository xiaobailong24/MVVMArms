package me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.xiaobailong24.mvvmarms.base.ArmsFragment;
import me.xiaobailong24.mvvmarms.weather.R;
import me.xiaobailong24.mvvmarms.weather.databinding.FragmentWeatherDailyBinding;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.Api;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter.WeatherDailyAdapter;
import me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel.WeatherDailyViewModel;
import me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel.WeatherViewModel;

/**
 * Created by xiaobailong24 on 2017/8/14.
 * MVVM WeatherDailyFragment
 */

public class WeatherDailyFragment extends ArmsFragment<FragmentWeatherDailyBinding, WeatherDailyViewModel> {

    private WeatherDailyAdapter mAdapter;
    @Inject
    WeatherViewModel mWeatherViewModel;//共享 Activity 数据

    public static WeatherDailyFragment newInstance(String location) {
        WeatherDailyFragment weatherDailyFragment = new WeatherDailyFragment();
        Bundle args = new Bundle();
        args.putString(Api.API_KEY_LOCATION, location);
        weatherDailyFragment.setArguments(args);
        return weatherDailyFragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WeatherDailyViewModel.class);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_daily, container, false);
        mBinding.setViewModel(mViewModel);//设置ViewModel
        //RecyclerView设置Adapter
        mAdapter = new WeatherDailyAdapter(R.layout.super_item_daily, null);
        mBinding.recyclerWeatherDaily.setAdapter(mAdapter);
        //设置Refresh
        mBinding.refresh.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorAccent),
                ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        return mBinding.getRoot();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //        observerWeatherDaily();
    }

    @SuppressWarnings("all")
    @Override
    public void setData(Object data) {
        /**
         * 可以在Activity中调用该接口，实现Activity与Fragment通信。建议 data 传递 {@link android.os.Message}；
         * 这样就可以根据 message.what 判断接收消息。
         * 但是：
         * 新姿势：可以通过 Activity 的 ViewModel 共享数据给包含的 Fragment，配合 LiveData 好用到爆。
         *  @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing_data_between_fragments">Sharing Data Between Fragments</a>
         */
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //当Fragment显示/隐藏变化时执行该方法，根据是否显示Fragment加载数据，实现懒加载
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (mViewModel != null) {
                //调用ViewModel的方法获取天气
                mViewModel.getWeatherDaily(mWeatherViewModel.getLocation().getValue())
                        .observe(WeatherDailyFragment.this, dailies -> {
                            mAdapter.replaceData(dailies);
                        });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
    }
}
