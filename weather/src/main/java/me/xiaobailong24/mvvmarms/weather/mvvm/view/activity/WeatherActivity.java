package me.xiaobailong24.mvvmarms.weather.mvvm.view.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import me.xiaobailong24.mvvmarms.base.ArmsActivity;
import me.xiaobailong24.mvvmarms.utils.UiUtils;
import me.xiaobailong24.mvvmarms.weather.R;
import me.xiaobailong24.mvvmarms.weather.app.EventBusTags;
import me.xiaobailong24.mvvmarms.weather.app.utils.FragmentUtils;
import me.xiaobailong24.mvvmarms.weather.app.utils.KeyboardUtils;
import me.xiaobailong24.mvvmarms.weather.databinding.ActivityWeatherBinding;
import me.xiaobailong24.mvvmarms.weather.mvvm.view.fragment.WeatherNowFragment;
import me.xiaobailong24.mvvmarms.weather.mvvm.viewModel.WeatherViewModel;

import static me.xiaobailong24.mvvmarms.weather.app.EventBusTags.ACTIVITY_FRAGMENT_REPLACE;

public class WeatherActivity extends ArmsActivity<ActivityWeatherBinding, WeatherViewModel> {
    private static final String LOCATION_KEY = "location";

    private int mReplace = 0;
    private String mLocation;
    private LiveData<List<String>> mHistoryLocations;

    @Override
    public int initView(Bundle savedInstanceState) {
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WeatherViewModel.class);
        return R.layout.activity_weather;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.setViewModel(mViewModel);
        if (savedInstanceState != null)
            mLocation = savedInstanceState.getString(LOCATION_KEY);
        setSupportActionBar(mBinding.searchToolbar);
        initToolbar();
    }

    private void initToolbar() {
        //Init Toolbar
        mBinding.searchToolbar.setOnClickListener(view -> {
            mBinding.searchView.showSearch();
        });
        //Init SearchView
        mHistoryLocations = mViewModel.getHistoryLocations();
        mHistoryLocations.observe(this, strings -> {
            if (strings.size() > 0) {
                mBinding.searchView.setSuggestions(strings.toArray(new String[0]));
                //Set Suggestions Listener
                mBinding.searchView.setOnItemClickListener((adapterView, view, i, l) -> {
                    String query = (String) adapterView.getItemAtPosition(i);
                    doSearch(query);
                });
                mLocation = strings.get(0);
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
        WeatherNowFragment weatherNowFragment =
                (WeatherNowFragment) FragmentUtils.findFragment(getSupportFragmentManager(), WeatherNowFragment.class);
        if (weatherNowFragment == null) {
            weatherNowFragment = WeatherNowFragment.newInstance(mLocation);
            FragmentUtils.addFragment(getSupportFragmentManager(), weatherNowFragment, R.id.main_frame);
        } else {
            Message message = new Message();
            message.what = EventBusTags.FRAGMENT_MESSAGE_WEATHER_NOW;
            message.obj = mLocation;
            weatherNowFragment.setData(message);
        }
    }

    //处理搜索时事件
    private void doSearch(String location) {
        if (TextUtils.isEmpty(location)) {
            UiUtils.makeText(this, getString(R.string.snack_input));
            return;
        }
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
        outState.putInt(ACTIVITY_FRAGMENT_REPLACE, mReplace);
        outState.putString(LOCATION_KEY, mLocation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mHistoryLocations = null;
        this.mLocation = null;
    }
}
