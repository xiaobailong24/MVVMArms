package me.xiaobailong24.mvvmarms.weather.mvvm.view.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import me.xiaobailong24.mvvmarms.base.ArmsActivity;
import me.xiaobailong24.mvvmarms.base.delegate.IFragment;
import me.xiaobailong24.mvvmarms.weather.R;
import me.xiaobailong24.mvvmarms.weather.app.EventBusTags;
import me.xiaobailong24.mvvmarms.weather.app.utils.KeyboardUtils;
import me.xiaobailong24.mvvmarms.weather.databinding.ActivityWeatherBinding;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.Api;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter.WeatherPagerAdapter;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment.WeatherDailyFragment;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment.WeatherNowFragment;
import me.xiaobailong24.mvvmarms.weather.mvvm.viewModel.WeatherViewModel;
import timber.log.Timber;

public class WeatherActivity extends ArmsActivity<ActivityWeatherBinding, WeatherViewModel> {

    private int mReplace = 0;
    private String mLocation = "北京";
    private LiveData<List<String>> mHistoryLocations;
    private List<Fragment> mFragments;
    private List<String> mFragmentTitles;

    @Override
    public int initView(Bundle savedInstanceState) {
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WeatherViewModel.class);
        return R.layout.activity_weather;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.setViewModel(mViewModel);
        if (savedInstanceState != null) {
            //Restore data
            mReplace = savedInstanceState.getInt(EventBusTags.ACTIVITY_FRAGMENT_REPLACE);
            mLocation = savedInstanceState.getString(Api.API_KEY_LOCATION);
        }
        setSupportActionBar(mBinding.searchToolbar);
        initViewPager();
        initToolbar();
    }

    private void initViewPager() {
        if (mFragments == null)
            mFragments = new ArrayList<>();
        if (mFragmentTitles == null)
            mFragmentTitles = new ArrayList<>();

        //ViewPager findFragmentByTag，tag= "android:switcher:" + R.id.viewpager + position
        WeatherNowFragment weatherNowFragment =
                (WeatherNowFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.weather_pager + ":" + 0);
        WeatherDailyFragment weatherDailyFragment =
                (WeatherDailyFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.weather_pager + ":" + 1);
        if (weatherNowFragment == null) {
            weatherNowFragment = WeatherNowFragment.newInstance(mLocation);
        }
        if (weatherDailyFragment == null) {
            weatherDailyFragment = WeatherDailyFragment.newInstance(mLocation);
        }
        mFragments.add(weatherNowFragment);
        mFragments.add(weatherDailyFragment);
        mFragmentTitles.add("Today");
        mFragmentTitles.add("Next Three");

        //Setup ViewPager
        WeatherPagerAdapter adapter =
                new WeatherPagerAdapter(getSupportFragmentManager(), mFragments, mFragmentTitles);
        mBinding.contentWeather.weatherPager.setAdapter(adapter);
        mBinding.contentWeather.tabLayout.setupWithViewPager(mBinding.contentWeather.weatherPager);
        mBinding.contentWeather.weatherPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Timber.i("onPageSelected: " + position);
                mReplace = position;
                naviFragment();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initToolbar() {
        //Init Toolbar
        mBinding.searchToolbar.setOnClickListener(view -> {
            mBinding.searchView.showSearch();
        });
        //Init SearchView
        mHistoryLocations = mViewModel.getHistoryLocations();
        mHistoryLocations.observe(this, locations -> {
            if (locations.size() > 0) {
                mBinding.searchView.setSuggestions(locations.toArray(new String[0]));
                //Set Suggestions Listener
                mBinding.searchView.setOnItemClickListener((adapterView, view, i, l) -> {
                    String query = (String) adapterView.getItemAtPosition(i);
                    doSearch(query);
                });
                if (TextUtils.equals(mLocation, "北京"))
                    mLocation = locations.get(0);
                naviFragment();
            }
        });
        mBinding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //切换Fragment
    private void naviFragment() {
        getSupportActionBar().setTitle(mLocation);
        IFragment fragment = (IFragment) mFragments.get(mReplace);
        Message message = new Message();
        switch (mReplace) {
            case 0:
                message.what = EventBusTags.FRAGMENT_MESSAGE_WEATHER_NOW;
                break;
            case 1:
                message.what = EventBusTags.FRAGMENT_MESSAGE_WEATHER_DAILY;
                break;
        }
        message.obj = mLocation;
        fragment.setData(message);
        mFragments.get(mReplace).onHiddenChanged(false);
    }

    //处理搜索时事件
    private void doSearch(String location) {
        mLocation = location;
        mBinding.searchView.closeSearch();
        KeyboardUtils.hideSoftInput(WeatherActivity.this, mBinding.searchView);
        naviFragment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        //Set SearchView MenuItem
        mBinding.searchView.setMenuItem(menu.findItem(R.id.action_search));
        return true;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    @Override
    public void onBackPressed() {
        //Close SearchView
        if (mBinding.searchView.isSearchOpen()) {
            mBinding.searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存当前Activity显示的Fragment索引
        outState.putInt(EventBusTags.ACTIVITY_FRAGMENT_REPLACE, mReplace);
        outState.putString(Api.API_KEY_LOCATION, mLocation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Remove Observer
        if (mHistoryLocations != null)
            mHistoryLocations.removeObservers(this);
        this.mHistoryLocations = null;
        this.mLocation = null;
        this.mFragments = null;
        this.mFragmentTitles = null;
    }
}
