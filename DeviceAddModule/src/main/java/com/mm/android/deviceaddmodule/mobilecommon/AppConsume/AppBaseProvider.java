package com.mm.android.deviceaddmodule.mobilecommon.AppConsume;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.mm.android.deviceaddmodule.mobilecommon.base.BaseProvider;

public class AppBaseProvider extends BaseProvider implements IApp {

    private static int appType = 0;

    @Override
    public void setAppType(int type) {
        appType = type;
    }

    @Override
    public int getAppType() {
        return  appType;
    }

    @Override
    public Context getAppContext() {
        return null;
    }

    @Override
    public void goDeviceSharePage(Activity activity, Bundle bundle) {

    }

    @Override
    public void goBuyCloudPage(Activity activity, Bundle bundle) {

    }

    @Override
    public void goModifyDevicePwdGuidePage(Activity activity) {

    }

    @Override
    public String getAppLanguage() {
        return null;
    }

}
