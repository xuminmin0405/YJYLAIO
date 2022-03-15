package com.ecare.yjylaio.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.RootActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.contract.DoctorContract;
import com.ecare.yjylaio.lechange.openapi.MethodConst;
import com.ecare.yjylaio.lechange.openapi.entity.DeviceDetailListData;
import com.ecare.yjylaio.lechange.ui.DeviceOnlineMediaPlayActivity;
import com.ecare.yjylaio.model.bean.rsp.UserHealthDataRspDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.presenter.DoctorPresenter;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: DoctorActivity
 * Description:
 * Author:
 * CreateDate: 2021/6/24 13:58
 * Version: 1.0
 */
public class DoctorActivity extends RootActivity<DoctorContract.Presenter> implements DoctorContract.View {

    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //加载组件
    @BindView(R.id.view_main)
    SmartRefreshLayout srlRefresh;
    //姓名
    @BindView(R.id.tv_name)
    TextView tvName;
    //血压
    @BindView(R.id.tv_blood_pressure)
    TextView tvBloodPressure;
    //血糖
    @BindView(R.id.tv_blood_sugar)
    TextView tvBloodSugar;
    //心率
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;
    //体温
    @BindView(R.id.tv_body_temperature)
    TextView tvBodyTemperature;
    //步数
    @BindView(R.id.tv_step_count)
    TextView tvStepCount;
    //身高
    @BindView(R.id.tv_height)
    TextView tvHeight;
    //体重
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    //身份证号
    private String mIdCard;
    //姓名
    private String mName;
    //乐橙设备数据
    private DeviceDetailListData.ResponseData.DeviceListBean mDevice;

    @Override
    protected DoctorContract.Presenter createPresenter() {
        return new DoctorPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_doctor;
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mIdCard = getIntent().getStringExtra(Constants.IT_ID_CARD);
        mName = getIntent().getStringExtra(Constants.IT_NAME);
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        //设置标题
        tvTitle.setText("健康兰里");
        //初始化SmartRefreshLayout
        initSmartRefreshLayout();
        //设置姓名
        tvName.setText("欢迎" + mName + "进入健康兰里！");
    }

    /**
     * 初始化SmartRefreshLayout
     */
    private void initSmartRefreshLayout() {
        srlRefresh.setEnableLoadMore(false);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getHealthData(CommonSubscriber.SHOW_REFRESH_LAYOUT, mIdCard);
            }
        });
    }

    /**
     * 点击事件
     *
     * @param view view
     */
    @OnClick({R.id.iv_back, R.id.iv_consulting, R.id.iv_registration, R.id.iv_doctor, R.id.iv_mission, R.id.tv_detail})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_consulting:
                if (mDevice == null) {
                    showMsg("乐橙设备数据无法获取");
                    break;
                }
                Disposable disposable = new RxPermissions(this)
                        .request(Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(MethodConst.ParamConst.deviceDetail, mDevice);
                                Intent intent = new Intent(mContext, DeviceOnlineMediaPlayActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                break;
            case R.id.iv_registration:
                showMsg("功能开发中，敬请期待");
                break;
            case R.id.iv_doctor:
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("jkt://jkt/doctorInfo?url=https%3A%2F%2Fwww.hfi-health.com%3A28181%2Fhlw%2FtempFirst.html%3FclientId%3D100000000006"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtils.showShort("手机还没有安装支持打开此网页的应用！");
                }
                break;
            case R.id.iv_mission:
                startActivity(new Intent(mContext, EntertainmentActivity.class));
                break;
            case R.id.tv_detail:
                intent = new Intent(mContext, WebPageActivity.class);
                intent.putExtra(Constants.IT_URL, Constants.URL_FITNESS_DETAIL + mIdCard);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doBusiness() {
        stateLoading();
        mPresenter.getHealthData(CommonSubscriber.SHOW_STATE, mIdCard);
        mPresenter.getYTJDevice();
    }

    /**
     * 设置单人健康数据
     *
     * @param userHealthData 单人健康数据
     */
    @Override
    public void setHealthData(UserHealthDataRspDTO userHealthData) {
        if (userHealthData == null) {
            return;
        }
        tvBloodPressure.setText(StringUtils.isEmpty(userHealthData.getBloodPressure()) ? "暂无数据" : userHealthData.getBloodPressure());
        tvBloodSugar.setText(StringUtils.isEmpty(userHealthData.getBloodSugar()) ? "暂无数据" : userHealthData.getBloodSugar());
        tvHeartRate.setText(StringUtils.isEmpty(userHealthData.getHeartRate()) ? "暂无数据" : userHealthData.getHeartRate());
        tvBodyTemperature.setText(StringUtils.isEmpty(userHealthData.getTemperature()) ? "暂无数据" : userHealthData.getTemperature());
        tvStepCount.setText(StringUtils.isEmpty(userHealthData.getStepss()) ? "暂无数据" : userHealthData.getStepss());
        tvHeight.setText(StringUtils.isEmpty(userHealthData.getHeight()) ? "暂无数据" : userHealthData.getHeight());
        tvWeight.setText(StringUtils.isEmpty(userHealthData.getWeight()) ? "暂无数据" : userHealthData.getWeight());
    }

    /**
     * 设置乐橙设备数据
     *
     * @param device 乐橙设备数据
     */
    @Override
    public void setYTJDevice(String device) {
        if (StringUtils.isEmpty(device)) {
            return;
        }
        try {
            mDevice = new Gson().fromJson(device, DeviceDetailListData.ResponseData.DeviceListBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
