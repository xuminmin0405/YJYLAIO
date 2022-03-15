package com.ecare.yjylaio.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.SimpleActivity;

import androidx.annotation.Nullable;
import me.jessyan.autosize.internal.CancelAdapt;

/**
 * ProjectName: YJYLMerchants
 * Package: com.ecare.yjylmerchants.ui.activity
 * ClassName: SplashActivity
 * Description:
 * Author:
 * CreateDate: 2021/5/1 14:14
 * Version: 1.0
 */
public class SplashActivity extends SimpleActivity implements CancelAdapt {

    @Override
    protected int getLayoutId() {
        return R.layout.act_splash;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    protected void doBusiness() {

    }
}
