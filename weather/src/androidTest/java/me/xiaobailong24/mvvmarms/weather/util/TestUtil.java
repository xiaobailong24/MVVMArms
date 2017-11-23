package me.xiaobailong24.mvvmarms.weather.util;

import me.xiaobailong24.mvvmarms.weather.mvvm.model.entry.Location;

/**
 * @author xiaobailong24
 * @date 2017/7/30
 * TestUtil
 */
public class TestUtil {

    public static Location createBeijing(String id) {
        /*
         * id : WX4FBXXFKE4F
         * name : 北京
         * country : CN
         * path : 北京,北京,中国
         * timezone : Asia/Shanghai
         * timezone_offset : +08:00
         */
        return new Location(id, "北京", "CN",
                "北京,北京,中国", "Asia/Shanghai", "+08:00");
    }

    public static Location createShanghai(String id) {
        /*
         * id : HBJGASKSJFBA
         * name : 上海
         * country : CN
         * path : 上海,上海,中国
         * timezone : Asia/Shanghai
         * timezone_offset : +08:00
         */
        return new Location(id, "上海", "CN",
                "上海,上海,中国", "Asia/Shanghai", "+08:00");
    }

}
