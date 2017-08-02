package me.xiaobailong24.mvvmarms.weather.mvvm.model.entry;

import me.xiaobailong24.mvvmarms.weather.mvvm.model.api.Api;

/**
 * Created by xiaobailong24 on 2017/7/27.
 * 天气请求基类，存放API_KEY
 */

public class BaseWeatherRequest {
    public String key = Api.API_KEY;
}
