package me.xiaobailong24.mvvmarms.weather.mvvm.model.entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author xiaobailong24
 * @date 2017/8/14
 * 未来三日天气结果
 */
public class WeatherDailyResponse {

    private List<DailyResult> results;

    public List<DailyResult> getResults() {
        return results;
    }

    public void setResults(List<DailyResult> results) {
        this.results = results;
    }

    public static class DailyResult {
        /**
         * location : {"id":"WX4FBXXFKE4F","name":"北京","country":"CN","path":"北京,北京,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
         * daily : [{"date":"2017-08-14","text_day":"阴","code_day":"9","text_night":"多云","code_night":"4","high":"30","low":"23","precip":"","wind_direction":"东南","wind_direction_degree":"135","wind_speed":"10","wind_scale":"2"},{"date":"2017-08-15","text_day":"阴","code_day":"9","text_night":"雷阵雨","code_night":"11","high":"30","low":"23","precip":"","wind_direction":"西南","wind_direction_degree":"225","wind_speed":"10","wind_scale":"2"},{"date":"2017-08-16","text_day":"雷阵雨","code_day":"11","text_night":"雷阵雨","code_night":"11","high":"29","low":"22","precip":"","wind_direction":"东南","wind_direction_degree":"135","wind_speed":"10","wind_scale":"2"}]
         * last_update : 2017-08-14T18:00:00+08:00
         */

        private Location location;
        @SerializedName("last_update")
        private String lastUpdate;
        private List<Daily> daily;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public List<Daily> getDaily() {
            return daily;
        }

        public void setDaily(List<Daily> daily) {
            this.daily = daily;
        }

        public static class Daily {
            /**
             * date : 2017-08-14
             * text_day : 阴
             * code_day : 9
             * text_night : 多云
             * code_night : 4
             * high : 30
             * low : 23
             * precip :
             * wind_direction : 东南
             * wind_direction_degree : 135
             * wind_speed : 10
             * wind_scale : 2
             */

            private String date;
            @SerializedName("text_day")
            private String textDay;
            @SerializedName("code_day")
            private String codeDay;
            @SerializedName("text_night")
            private String textNight;
            @SerializedName("code_night")
            private String codeNight;
            private String high;
            private String low;
            private String precip;
            @SerializedName("wind_direction")
            private String windDirection;
            @SerializedName("wind_direction_degree")
            private String windDirectionDegree;
            @SerializedName("wind_speed")
            private String windSpeed;
            @SerializedName("wind_scale")
            private String windScale;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTextDay() {
                return textDay;
            }

            public void setTextDay(String textDay) {
                this.textDay = textDay;
            }

            public String getCodeDay() {
                return codeDay;
            }

            public void setCodeDay(String codeDay) {
                this.codeDay = codeDay;
            }

            public String getTextNight() {
                return textNight;
            }

            public void setTextNight(String textNight) {
                this.textNight = textNight;
            }

            public String getCodeNight() {
                return codeNight;
            }

            public void setCodeNight(String codeNight) {
                this.codeNight = codeNight;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getPrecip() {
                return precip;
            }

            public void setPrecip(String precip) {
                this.precip = precip;
            }

            public String getWindDirection() {
                return windDirection;
            }

            public void setWindDirection(String windDirection) {
                this.windDirection = windDirection;
            }

            public String getWindDirectionDegree() {
                return windDirectionDegree;
            }

            public void setWindDirectionDegree(String windDirectionDegree) {
                this.windDirectionDegree = windDirectionDegree;
            }

            public String getWindSpeed() {
                return windSpeed;
            }

            public void setWindSpeed(String windSpeed) {
                this.windSpeed = windSpeed;
            }

            public String getWindScale() {
                return windScale;
            }

            public void setWindScale(String windScale) {
                this.windScale = windScale;
            }
        }
    }
}
