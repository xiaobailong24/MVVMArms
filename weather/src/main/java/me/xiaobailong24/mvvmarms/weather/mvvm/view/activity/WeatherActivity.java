package me.xiaobailong24.mvvmarms.weather.mvvm.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import me.xiaobailong24.mvvmarms.base.ArmsActivity;
import me.xiaobailong24.mvvmarms.weather.R;
import me.xiaobailong24.mvvmarms.weather.app.EventBusTags;
import me.xiaobailong24.mvvmarms.weather.app.utils.KeyboardUtils;
import me.xiaobailong24.mvvmarms.weather.databinding.ActivityWeatherBinding;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.adapter.WeatherPagerAdapter;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment.WeatherDailyFragment;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment.WeatherNowFragment;
import me.xiaobailong24.mvvmarms.weather.mvvm.viewmodel.WeatherViewModel;
import timber.log.Timber;

public class WeatherActivity extends ArmsActivity<ActivityWeatherBinding, WeatherViewModel> {

    private int mReplace = 0;
    private List<Fragment> mFragments;
    private List<String> mFragmentTitles;

    @Override
    public int initView(Bundle savedInstanceState) {
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WeatherViewModel.class);
        if (savedInstanceState != null) {
            //Restore data
            mReplace = savedInstanceState.getInt(EventBusTags.ACTIVITY_FRAGMENT_REPLACE);
        }
        return R.layout.activity_weather;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.setViewModel(mViewModel);
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
        WeatherNowFragment weatherNowFragment = (WeatherNowFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.weather_pager + ":" + 0);
        WeatherDailyFragment weatherDailyFragment = (WeatherDailyFragment) getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.weather_pager + ":" + 1);
        if (weatherNowFragment == null) {
            weatherNowFragment = WeatherNowFragment.newInstance(mViewModel.getLocation().getValue());
        }
        if (weatherDailyFragment == null) {
            weatherDailyFragment = WeatherDailyFragment.newInstance(mViewModel.getLocation().getValue());
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
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @SuppressWarnings("all")
    private void initToolbar() {
        //Init Toolbar
        mViewModel.getLocation().observe(this, s ->
                getSupportActionBar().setTitle(s));
        mBinding.searchToolbar.setOnClickListener(view -> {
            mBinding.searchView.showSearch();
        });
        //Init SearchView,自动取消订阅
        mViewModel.getHistoryLocations().observe(this, locations -> {
            if (locations.size() > 0) {
                mBinding.searchView.setSuggestions(locations.toArray(new String[0]));
                //Set Suggestions Listener
                mBinding.searchView.setOnItemClickListener((adapterView, view, i, l) -> {
                    String query = (String) adapterView.getItemAtPosition(i);
                    doSearch(query);
                });
                String location = locations.get(0);
                if (location.contains(",")) //如果位置是全路径，则截取城市名
                    location = location.substring(0, location.indexOf(","));
                if (TextUtils.equals(mViewModel.getLocation().getValue(), "北京"))
                    mViewModel.getLocation().setValue(location);
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


    //处理搜索事件
    private void doSearch(String location) {
        if (location.contains(",")) //如果位置是全路径，则截取城市名
            location = location.substring(0, location.indexOf(","));
        mViewModel.getLocation().setValue(location);
        mBinding.searchView.closeSearch();
        KeyboardUtils.hideSoftInput(WeatherActivity.this, mBinding.searchView);
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
        //保存当前 Activity 显示的 Fragment 索引
        outState.putInt(EventBusTags.ACTIVITY_FRAGMENT_REPLACE, mReplace);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mFragments = null;
        this.mFragmentTitles = null;
    }
}
