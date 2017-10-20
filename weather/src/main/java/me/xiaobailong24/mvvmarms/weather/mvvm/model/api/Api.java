package me.xiaobailong24.mvvmarms.weather.mvvm.model.api;

/**
 * @author xiaobailong24
 * @date 2017/7/25
 * 心知天气API
 */
public interface Api {
    /**
     * HTTP Url
     */
    String APP_DOMAIN = "https://api.seniverse.com/v3/";

    /**
     * 心知天气API密钥
     */
    String API_KEY = "sokppqeydnrkohxe";

    /**
     * 心知天气API图标
     */
    String API_WEATHER_ICON_URL = "https://s3.sencdn.com/web/icons/3d_50/%s.png";

    /**
     * Api Key
     */
    String API_KEY_KEY = "key";

    /**
     * API Language
     */
    String API_KEY_LANGUAGE = "language";

    /**
     * Api temperature unit
     */
    String API_KEY_TEMP_UNIT = "unit";

    /**
     * Api location
     */
    String API_KEY_LOCATION = "location";
}
