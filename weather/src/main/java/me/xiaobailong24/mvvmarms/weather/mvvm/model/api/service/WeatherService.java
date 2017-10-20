package me.xiaobailong24.mvvmarms.weather.mvvm.model.api.service;

import java.util.Map;

import io.reactivex.Observable;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherDailyResponse;
import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.WeatherNowResponse;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Api Retrofit WeatherService
 */
public interface WeatherService {

    /**
     * 获取指定城市的天气实况。付费用户可获取全部数据，免费用户只返回天气现象文字、代码和气温3项数据。
     *
     * @param request 请求参数
     * @return WeatherNowResponse
     * @see <a href="https://api.seniverse.com/v3/weather/now.json?key=sokppqeydnrkohxe&location=beijing&language=zh-Hans&unit=c"/>
     */
    @GET("weather/now.json")
    Observable<WeatherNowResponse> getWeatherNow(@QueryMap Map<String, String> request);


    /**
     * 获取指定城市未来3天每天的白天和夜间预报，免费用户只返回3天天气预报。
     *
     * @param request 请求参数
     * @return WeatherDailyResponse
     * @see <a href="https://api.seniverse.com/v3/weather/daily.json?key=sokppqeydnrkohxe&location=beijing&language=zh-Hans&unit=c"/>
     */
    @GET("weather/daily.json")
    Observable<WeatherDailyResponse> getWeatherDaily(@QueryMap Map<String, String> request);

}
