package me.xiaobailong24.mvvmarms.weather.mvvm.model.entry;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author xiaobailong24
 * @date 2017/8/15
 * 位置信息，存储数据库
 */
@Entity(tableName = "Location")
public class Location {
    /**
     * id : WX4FBXXFKE4F
     * name : 北京
     * country : CN
     * path : 北京,北京,中国
     * timezone : Asia/Shanghai
     * timezone_offset : +08:00
     */
    @NonNull
    @PrimaryKey
    private String id;
    private String name;
    private String country;
    private String path;
    private String timezone;
    @SerializedName("timezone_offset")
    @ColumnInfo(name = "timezone_offset")
    private String timezoneOffset;

    public Location(@NonNull String id, String name, String country, String path, String timezone, String timezoneOffset) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.path = path;
        this.timezone = timezone;
        this.timezoneOffset = timezoneOffset;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

