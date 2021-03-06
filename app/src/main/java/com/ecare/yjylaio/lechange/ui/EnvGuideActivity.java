package com.ecare.yjylaio.lechange.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ecare.yjylaio.R;
import com.ecare.yjylaio.lechange.tools.RootUtil;
import com.mm.android.deviceaddmodule.CommonParam;
import com.mm.android.deviceaddmodule.LCDeviceEngine;
import com.mm.android.deviceaddmodule.mobilecommon.AppConsume.ProviderManager;
import com.mm.android.deviceaddmodule.mobilecommon.utils.PreferencesHelper;
import com.mm.android.deviceaddmodule.mobilecommon.widget.ClearEditText;
import com.mm.android.deviceaddmodule.openapi.CONST;
import com.mm.android.deviceaddmodule.utils.SDsolutionUtility;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class EnvGuideActivity extends Activity implements View.OnClickListener {

    private TextView tvTitleCn;
    private TextView tvOverseas;
    private TextView tvTip1;
    private ClearEditText tvUrl;
    private ClearEditText tvAk;
    private ClearEditText tvSk;
    private TextView tvSure;
    private TextView tvTip2;
    private boolean isChina;
    private static final String DOMESTIC_APP_ID_KEY = "DOMESTIC_APP_ID";
    private static final String DOMESTIC_APP_SECRET_KEY = "DOMESTIC_APP_SECRET";
    private static final String OVERSEAS_APP_ID_KEY = "OVERSEAS_APP_ID";
    private static final String OVERSEAS_APP_SECRET_KEY = "OVERSEAS_APP_SECRET";
    private static final String DOMESTIC_URL = "DOMESTIC_URL";
    private static final String OVERSEAS_URL = "OVERSEAS_URL";
    private String appSecret = "";
    private String appId = "";
    private String url = "";
    private TextView tvUrlTip;
    private boolean initing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_guide);
        initVIew();
        initData();
        boolean deviceRooted = false;
        try {
            deviceRooted = RootUtil.isDeviceRooted();
            if (deviceRooted) {
                Toast.makeText(this,"????????????Root",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initCache();

    }

    private void initCache(){
        SDsolutionUtility.initContext(getApplicationContext());
        SDsolutionUtility.createDir("demo");
    }

    private void initVIew() {
        tvTitleCn = findViewById(R.id.tv_title_cn);
        tvOverseas = findViewById(R.id.tv_overseas);
        tvTip1 = findViewById(R.id.tv_tip1);
        tvUrl = findViewById(R.id.tv_url);
        tvAk = findViewById(R.id.tv_ak);
        tvSk = findViewById(R.id.tv_sk);
        tvSure = findViewById(R.id.tv_sure);
        tvTip2 = findViewById(R.id.tv_tip2);
        tvUrlTip = findViewById(R.id.tv_url_tip);
        tvTitleCn.setOnClickListener(this);
        tvOverseas.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        tvUrlTip.setMovementMethod(LinkMovementMethod.getInstance());
        tvUrlTip.setHighlightColor(Color.TRANSPARENT);
        tvAk.setFocusable(true);
        tvAk.setFocusableInTouchMode(true);
        tvAk.requestFocus();
        //???????????????
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void initData() {
        switchEnv(true);
        requestPermission();
        tvAk.setSelection(tvAk.getText().toString().trim().length());
    }

    /**
     * ??????????????????
     */
    public void requestPermission() {
        boolean isMinSDKM = Build.VERSION.SDK_INT < 23;
        boolean isGranted = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (isMinSDKM || isGranted) {
            return;
        }
        requestRecordAudioPermission();
    }

    private void requestRecordAudioPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_title_cn) {
            switchEnv(true);
        } else if (id == R.id.tv_overseas) {
            switchEnv(false);
        } else if (id == R.id.tv_sure) {
            if (initing) {
                return;
            }
            String appId = tvAk.getText().toString().trim();
            String appSecret = tvSk.getText().toString().trim();
            String url = tvUrl.getText().toString().trim();
            if (TextUtils.isEmpty(url)) {
                Toast.makeText(EnvGuideActivity.this, "url ????????????", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(appId)) {
                Toast.makeText(EnvGuideActivity.this, "appID ????????????", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(appSecret)) {
                Toast.makeText(EnvGuideActivity.this, "appSecret ????????????", Toast.LENGTH_SHORT).show();
                return;
            }
            initing = true;
            try {
                CommonParam commonParam = new CommonParam();
                commonParam.setEnvirment(url);
                commonParam.setContext(EnvGuideActivity.this.getApplication());
                commonParam.setAppId(appId);
                commonParam.setAppSecret(appSecret);
                LCDeviceEngine.newInstance().init(commonParam);
                //startActivity(new Intent(EnvGuideActivity.this, LoginActivity.class));
                if (isChina) {
                    PreferencesHelper.getInstance(EnvGuideActivity.this).set(DOMESTIC_APP_ID_KEY, appId);
                    PreferencesHelper.getInstance(EnvGuideActivity.this).set(DOMESTIC_APP_SECRET_KEY, appSecret);
                    PreferencesHelper.getInstance(EnvGuideActivity.this).set(DOMESTIC_URL, url);
                } else {
                    PreferencesHelper.getInstance(EnvGuideActivity.this).set(OVERSEAS_APP_ID_KEY, appId);
                    PreferencesHelper.getInstance(EnvGuideActivity.this).set(OVERSEAS_APP_SECRET_KEY, appSecret);
                    PreferencesHelper.getInstance(EnvGuideActivity.this).set(OVERSEAS_URL, url);
                }
            } catch (Throwable e) {
                Toast.makeText(EnvGuideActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            initing = false;
        }
    }

    private void switchEnv(boolean isChina) {
        this.isChina = isChina;
        if (isChina) {
            ProviderManager.getAppProvider().setAppType(0);
            //appId = PreferencesHelper.getInstance(this).getString(DOMESTIC_APP_ID_KEY, "lce022e0492fcd43e1");
            //appSecret = PreferencesHelper.getInstance(this).getString(DOMESTIC_APP_SECRET_KEY, "06c0d7e49b06443fa0f2fc3e78c7be");
            // appId = PreferencesHelper.getInstance(this).getString(DOMESTIC_APP_ID_KEY, "lc05ea872d861f426c");
           // appSecret = PreferencesHelper.getInstance(this).getString(DOMESTIC_APP_SECRET_KEY, "dab8eaca0fe64762a5e74efa3c252e");
            url = PreferencesHelper.getInstance(this).getString(DOMESTIC_URL, CONST.Envirment.CHINA_PRO.url);
            tvAk.setText(appId);
            tvSk.setText(appSecret);
            tvUrl.setText(url);
        } else {
            ProviderManager.getAppProvider().setAppType(1);
           // appId = PreferencesHelper.getInstance(this).getString(OVERSEAS_APP_ID_KEY, "lc778d9d6e9219485f");
           // appSecret = PreferencesHelper.getInstance(this).getString(OVERSEAS_APP_SECRET_KEY, "eb9e555c480a49aabfc55248075fd9");
            appId = PreferencesHelper.getInstance(this).getString(OVERSEAS_APP_ID_KEY, "lc687a087fa81645ab");
            appSecret = PreferencesHelper.getInstance(this).getString(OVERSEAS_APP_SECRET_KEY, "ebd47e66cae44522835661c41ba10c");
            url = PreferencesHelper.getInstance(this).getString(OVERSEAS_URL, CONST.Envirment.OVERSEAS_PRO.url);
            tvAk.setText(appId);
            tvSk.setText(appSecret);
            tvUrl.setText(url);
        }
        tvTitleCn.setSelected(isChina);
        tvOverseas.setSelected(!isChina);
        tvTip1.setText(isChina ? getString(R.string.lc_demo_cn_title_tip) : getString(R.string.lc_demo_overseas_title_tip));
        tvTip2.setText(isChina ? getString(R.string.lc_demo_choose_env_tip3) : getString(R.string.lc_demo_choose_env_tip4));
        String str1 = getResources().getString(isChina ? R.string.lc_demo_cn_title_tip : R.string.lc_demo_overseas_title_tip);
        String str2 = getResources().getString(R.string.lc_demo_choose_env_tip2);
        SpannableString info = new SpannableString(str1 + str2);
        if (!TextUtils.isEmpty(str1)) {
            info.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lc_demo_color_2c2c2c)), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (!TextUtils.isEmpty(str2)) {
            info.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lc_demo_color_8f8f8f)), str1.length(), str1.length() + str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvUrlTip.setText(info);
    }
}
