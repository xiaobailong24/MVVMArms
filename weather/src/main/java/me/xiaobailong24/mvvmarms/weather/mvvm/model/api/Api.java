package me.xiaobailong24.mvvmarms.weather.mvvm.model.api;

/**
 * Created by xiaobailong24 on 2017/7/25.
 * 心知天气API
 */

public interface Api {
    String APP_DOMAIN = "https://api.seniverse.com/v3/";
    String API_KEY = "sokppqeydnrkohxe";//心知天气API密钥

    String API_KEY_KEY = "key";
    String API_KEY_LANGUAGE = "language";
    String API_KEY_TEMP_UNIT = "unit";
    String API_KEY_LOCATION = "location";

    //数据请求状态
    enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}
