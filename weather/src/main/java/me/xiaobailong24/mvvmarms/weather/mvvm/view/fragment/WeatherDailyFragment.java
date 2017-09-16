package me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.xiaobailong24.mvvmarms.base.ArmsFragment;
import me.xiaobailong24.mvvmarms.weather.R;
import me.xiaobailong24.mvvmarms.weather.app.EventBusTags;
import me.xiaobailong24.mvvmarms.weather.databinding.FragmentWeatherDailyBinding;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.Api;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherDailyResponse;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter.WeatherDailyAdapter;
import me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel.WeatherDailyViewModel;
import timber.log.Timber;

/**
 * Created by xiaobailong24 on 2017/8/14.
 * MVVM WeatherDailyFragment
 */

public class WeatherDailyFragment extends ArmsFragment<FragmentWeatherDailyBinding, WeatherDailyViewModel> {

    private WeatherDailyAdapter mAdapter;
    private LiveData<List<WeatherDailyResponse.DailyResult.Daily>> mWeatherDailyData;
    private MutableLiveData<String> mLocation = new MutableLiveData<>();//订阅搜索位置的变化

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
        if (savedInstanceState == null)
            mLocation.setValue(getArguments().getString(Api.API_KEY_LOCATION));
    }

    @Override
    public void setData(Object data) {
        Message message = (Message) data;
        Timber.i("setData: message.what--->" + message.what +
                ", message.obj--->" + message.obj);
        switch (message.what) {
            case EventBusTags.FRAGMENT_MESSAGE_WEATHER_DAILY:
                mLocation.setValue(String.valueOf(message.obj));
                break;
            default:
                break;
        }
    }

    //调用ViewModel的方法获取天气
    @SuppressWarnings("all")
    private void observerWeatherDaily() {
        mLocation.observe(this, location -> {
            //如果位置变化，则重新订阅
            if (mWeatherDailyData != null) {
                mWeatherDailyData.removeObservers(this);
                mWeatherDailyData = null;
            }
        });
        if (mViewModel != null) {
            if (mWeatherDailyData == null) {
                String location = mLocation.getValue();
                //如果位置是全路径，则截取城市名
                if (location.contains(","))
                    location = location.substring(0, location.indexOf(","));
                mWeatherDailyData = mViewModel.getWeatherDaily(location);
                mWeatherDailyData.observe(this, dailies -> {
                    mAdapter.replaceData(dailies);
                    // TODO: 2017/8/19
                    //            DiffUtil.DiffResult diffResult = DiffUtil
                    //                    .calculateDiff(new RecyclerViewDiffCallback<>(mAdapter.getData(), dailies));
                    //            diffResult.dispatchUpdatesTo(mAdapter);
                });
            }
        } else {
            mAdapter.replaceData(mWeatherDailyData.getValue());
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //当Fragment显示/隐藏变化时执行该方法，根据是否显示Fragment加载数据，实现懒加载
        super.onHiddenChanged(hidden);
        if (!hidden)
            observerWeatherDaily();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWeatherDailyData != null)//取消订阅
            mWeatherDailyData.removeObservers(this);
        this.mWeatherDailyData = null;
        this.mAdapter = null;
        this.mLocation = null;
    }
}
