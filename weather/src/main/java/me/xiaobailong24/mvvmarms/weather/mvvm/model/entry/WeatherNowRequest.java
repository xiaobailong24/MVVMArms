package me.xiaobailong24.mvvmarms.weather.mvvm.model.entry;

/**
 * Created by xiaobailong24 on 2017/7/27.
 * 天气实况请求体
 * https://api.seniverse.com/v3/weather/now.json?
 * key=sokppqeydnrkohxe&
 * location=beijing&
 * language=zh-Hans&
 * unit=c
 */

public class WeatherNowRequest extends BaseWeatherRequest{
    private String language;
    private String unit;
    private String location;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
