package com.mm.android.deviceaddmodule.mobilecommon.route;

public class RoutePathManager {
    private final static String ActivityPrefix = "activity/";

    //模块名
    private interface ModuleName {
        String COMMON_MODULE = "/app/";
        String USER_MODULE = "/usermodule/";
    }

    public interface ActivityPath{
        public static final String DeviceListActivityPath = ModuleName.COMMON_MODULE + ActivityPrefix + "DeviceListActivity";
        public static final String UserRegesiterPath = ModuleName.USER_MODULE + ActivityPrefix + "UserRegesiterActivity";
        public static final String LoginActivityPath = ModuleName.USER_MODULE + ActivityPrefix + "LoginActivity";

    }
}
