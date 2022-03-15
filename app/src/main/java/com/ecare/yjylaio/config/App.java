package com.ecare.yjylaio.config;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.ecare.yjylaio.lechange.openapi.ClassInstanceManager;
import com.ecare.yjylaio.lechange.tools.MediaPlayHelper;
import com.mm.android.deviceaddmodule.CommonParam;
import com.mm.android.deviceaddmodule.LCDeviceEngine;
import com.mm.android.deviceaddmodule.openapi.CONST;
import com.tencent.bugly.Bugly;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/16.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * Application
 */
public class App extends MultiDexApplication {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化工具类
        Utils.init(this);
        //初始化Bugly
        Bugly.init(getApplicationContext(), "925ea87cde", false);
        //lechange
        MediaPlayHelper.initContext(getApplicationContext());
        ClassInstanceManager.newInstance().init(this);
        try {
            CommonParam commonParam = new CommonParam();
            commonParam.setEnvirment(CONST.Envirment.CHINA_PRO.url);
            commonParam.setContext(this);
            commonParam.setAppId(Constants.KEY_LECHANGE_APP_ID);
            commonParam.setAppSecret(Constants.KEY_LECHANGE_APP_SECRET);
            LCDeviceEngine.newInstance().init(commonParam);
            LCDeviceEngine.newInstance().setSubAccessToken(LCDeviceEngine.newInstance().accessToken);
        } catch (Throwable e) {
            ToastUtils.showShort(e.getMessage());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}