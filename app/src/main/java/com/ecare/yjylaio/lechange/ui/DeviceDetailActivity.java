package com.ecare.yjylaio.lechange.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecare.yjylaio.R;
import com.ecare.yjylaio.lechange.openapi.IGetDeviceInfoCallBack;
import com.mm.android.deviceaddmodule.device_wifi.DeviceConstant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class DeviceDetailActivity extends AppCompatActivity implements View.OnClickListener , IGetDeviceInfoCallBack.IModifyDeviceName {

    public LinearLayout llOperate;
    public LinearLayout llBack;
    public TextView tvTitle;
    public RelativeLayout rlTitle;
    public FrameLayout frContent;
    public RelativeLayout rlLoading;
    private Bundle bundle;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        initView();
        initData();
        gotoDetailMainPage();
    }

    private void initData() {
        bundle = getIntent().getExtras();
    }

    private void initView() {
        llOperate = findViewById(R.id.ll_operate);
        llBack = findViewById(R.id.ll_back);
        tvTitle = findViewById(R.id.tv_title);
        rlTitle = findViewById(R.id.rl_title);
        frContent = findViewById(R.id.fr_content);
        rlLoading = findViewById(R.id.rl_loading);
        llOperate.setOnClickListener(this);
        llBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_back) {
            goBack();
        } else if (id == R.id.ll_operate) {

        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        if (llOperate != null) {
            llOperate.setVisibility(View.GONE);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            if(!TextUtils.isEmpty(name)){
                Intent intent = new Intent();
                intent.putExtra(DeviceConstant.IntentKey.DHDEVICE_NEW_NAME, name);
                setResult(100, intent);
            }
            finish();
        }
    }

    public void gotoDetailMainPage() {
        DeviceDetailMainFragment fragment = DeviceDetailMainFragment.newInstance();
        fragment.setArguments(bundle);
        fragment.setModifyNameListener(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fr_content, fragment).addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void deviceName(String newName) {
        name = newName;
    }
}
