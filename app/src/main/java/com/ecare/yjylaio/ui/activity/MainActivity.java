package com.ecare.yjylaio.ui.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.BaseActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.contract.MainContract;
import com.ecare.yjylaio.presenter.MainPresenter;
import com.ecare.yjylaio.rtc.activity.AliRtcChatActivity;
import com.ecare.yjylaio.widght.SwipePromptPopupView;
import com.lxj.xpopup.XPopup;
import com.mwcard.Reader;
import com.mwcard.ReaderAndroidUsb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: MainActivity
 * Description:
 * Author:
 * CreateDate: 2021/6/17 17:39
 * Version: 1.0
 */
public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {

    //USB读卡
    private UsbManager mUsbManager;
    private static final String DEVICE_USB = "com.android.example.USB";
    public static ReaderAndroidUsb mReaderAndroidUsb = null;
    public static Reader mReader = null;

    @BindView(R.id.tv_passenger_flow)
    TextView tvPassengerFlow;

    //客流量
    private int mEnteredTotal;
    //客流量请求定时
    private Disposable mSubscribe;

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.iv_bg, R.id.ll_smart_meal, R.id.ll_remote_consultation, R.id.ll_health_indicators, R.id.ll_self_assessment, R.id.ll_chinese_Medicine, R.id.ll_old_age, R.id.ll_have_fun})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_bg:
                intent = new Intent(mContext, MonitorActivity.class);
                intent.putExtra(Constants.IT_NAME, mEnteredTotal);
                startActivity(intent);
                break;
            case R.id.ll_smart_meal:
                intent = new Intent(mContext, WebPageActivity.class);
                intent.putExtra(Constants.IT_URL, Constants.URL_SMART_MEAL);
                startActivity(intent);
                //startActivity(new Intent(mContext, EntertainmentActivity.class));
                break;
            case R.id.ll_remote_consultation:
                connectDevice();
                new XPopup.Builder(mContext)
                        .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                        .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                        .asCustom(new SwipePromptPopupView(mContext, 1))
                        .show();
                //intent = new Intent(mContext, AliRtcChatActivity.class);
                //intent.putExtra(Constants.IT_ID_CARD, "330121195812231127");
                //intent.putExtra(Constants.IT_NAME, "来暖庆");
                //startActivity(intent);
                break;
            case R.id.ll_health_indicators:
                intent = new Intent(mContext, WebPageActivity.class);
                intent.putExtra(Constants.IT_URL, Constants.URL_HEALTH_INDICATORS);
                startActivity(intent);
                break;
            case R.id.ll_self_assessment:
                connectDevice();
                new XPopup.Builder(mContext)
                        .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                        .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                        .asCustom(new SwipePromptPopupView(mContext, 2))
                        .show();
//                intent = new Intent(mContext, SelfAssessmentNewActivity.class);
//                intent.putExtra(Constants.IT_ID_CARD, "330121195812231127");
//                startActivity(intent);
                break;
            case R.id.ll_chinese_Medicine:
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.URL_CHINESE_MEDICINE));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtils.showShort("手机还没有安装支持打开此网页的应用！");
                }
                break;
            case R.id.ll_old_age:
                startActivity(new Intent(mContext, OldAgeActivity.class));
                //ToastUtils.showShort("敬请期待");
                break;
            case R.id.ll_have_fun:
                startActivity(new Intent(mContext, HelpActivity.class));
                //startActivity(new Intent(mContext, CheatedActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void doBusiness() {
        mSubscribe = Flowable.interval(0, 5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        mPresenter.getEnteredTotal();
                    }
                });
    }

    /**
     * 连接USB设备
     */
    private void connectDevice() {
        //获取USB管理器
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        //获取一个已连接的USB设备，并且包含方法，以访问其标识信息、 接口和端点
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        if (deviceList.size() == 0) {
            ToastUtils.showShort("未找到设备");
            return;
        }
        //获取deviceList迭代器
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        //判断迭代器中是否有元素
        while (deviceIterator.hasNext()) {
            //如果有，获取元素
            UsbDevice usbDevice = deviceIterator.next();
            if (!ReaderAndroidUsb.isSupported(usbDevice)) {
                continue;
            }
            //判断是否拥有该设备的连接权限
            if (!mUsbManager.hasPermission(usbDevice)) {
                //如果没有则请求权限
                PendingIntent mPermissionIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        new Intent(DEVICE_USB), PendingIntent.FLAG_UPDATE_CURRENT);
                /*
                 * 展示征求用户同意连接这个设备的权限的对话框。 当用户回应这个对话框时,
                 * 广播接收器就会收到一个包含用一个boolean值来表示结果的EXTRA_PERMISSION_GRANTED字段的意图。
                 * 在连接设备之前检查这个字段的值是否为true和设备之间的“交流”
                 */
                mUsbManager.requestPermission(usbDevice, mPermissionIntent);
            } else {
                //如果已经拥有该设备的连接权限，直接对该设备操作
                mReaderAndroidUsb = new ReaderAndroidUsb(mUsbManager);
                try {
                    int st = mReaderAndroidUsb.openReader(usbDevice);
                    if (st >= 0) {
                        mReader = mReaderAndroidUsb;
                        ToastUtils.showShort("读写器连接成功");
                    } else {
                        ToastUtils.showShort("读写器连接失败");
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    ToastUtils.showShort("读写器连接出错");
                }
            }
        }
    }

    @Override
    public void setEnteredTotal(Integer data) {
        mEnteredTotal = data;
        tvPassengerFlow.setText("今日客流：" + data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscribe.dispose();
    }
}
