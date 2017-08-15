package me.xiaobailong24.mvvmarms.weather.mvvm.model.entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiaobailong24 on 2017/7/26.
 * 天气实况结果
 */
public class WeatherNowResponse {

    private List<NowResult> results;

    public List<NowResult> getResults() {
        return results;
    }

    public void setResults(List<NowResult> results) {
        this.results = results;
    }

    public static class NowResult {
        /**
         * location : {"id":"WX4FBXXFKE4F","name":"北京","country":"CN","path":"北京,北京,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
         * now : {"text":"阴","code":"9","temperature":"26"}
         * last_update : 2017-07-27T09:50:00+08:00
         */
        private Location location;
        private Now now;
        @SerializedName("last_update")
        private String lastUpdate;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public static class Now {
            /**
             * text : 阴
             * code : 9
             * temperature : 26
             */
            private String text;
            private String code;
            private String temperature;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }
    }
}
