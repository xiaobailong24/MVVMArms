package me.xiaobailong24.mvvmarms.weather.mvvm.model.entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xiaobailong24 on 2017/7/26.
 * 天气实况结果
 */
//@Entity(tableName = "WeatherNowResponse")
public class WeatherNowResponse {

    //    public int id = 0;

    //    @Ignore
    //    @Embedded
    //    @PrimaryKey
    private List<NowResult> results;

    public List<NowResult> getResults() {
        return results;
    }

    public void setResults(List<NowResult> results) {
        this.results = results;
    }

    //    @Entity(tableName = "WeatherNowResponseNowResult")
    public static class NowResult {
        /**
         * location : {"id":"WX4FBXXFKE4F","name":"北京","country":"CN","path":"北京,北京,中国","timezone":"Asia/Shanghai","timezone_offset":"+08:00"}
         * now : {"text":"阴","code":"9","temperature":"26"}
         * last_update : 2017-07-27T09:50:00+08:00
         */
        //        @PrimaryKey
        //        @Embedded
        private Location location;
        //        @Embedded
        private Now now;
        @SerializedName("last_update")
        //        @ColumnInfo(name = "last_update")
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

        @Entity(tableName = "Location")
        public static class Location {
            /**
             * id : WX4FBXXFKE4F
             * name : 北京
             * country : CN
             * path : 北京,北京,中国
             * timezone : Asia/Shanghai
             * timezone_offset : +08:00
             */
            @PrimaryKey
            private String id;
            private String name;
            private String country;
            private String path;
            private String timezone;
            @SerializedName("timezone_offset")
            @ColumnInfo(name = "timezone_offset")
            private String timezoneOffset;

            public Location(String id, String name, String country, String path, String timezone, String timezoneOffset) {
                this.id = id;
                this.name = name;
                this.country = country;
                this.path = path;
                this.timezone = timezone;
                this.timezoneOffset = timezoneOffset;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(String timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            @Override
            public String toString() {
                return "Location: "
                        + "\nid--->" + this.getId()
                        + "\nname--->" + this.getName()
                        + "\ncountry--->" + this.getCountry()
                        + "\npath--->" + this.getPath()
                        + "\ntimezone--->" + this.getTimezone()
                        + "\ntimezone_offset--->" + this.getTimezoneOffset()
                        + "\n";
            }
        }

        //        @Entity
        public static class Now {
            /**
             * text : 阴
             * code : 9
             * temperature : 26
             */
            //                        @PrimaryKey
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
