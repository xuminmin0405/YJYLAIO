package com.ecare.yjylaio.ui.activity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.SimpleActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.widght.SwipePromptPopupView;
import com.lxj.xpopup.XPopup;
import com.mwcard.Reader;
import com.mwcard.ReaderAndroidUsb;

import java.util.HashMap;
import java.util.Iterator;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: MainActivity
 * Description:
 * Author:
 * CreateDate: 2021/6/17 17:39
 * Version: 1.0
 */
public class MainActivity extends SimpleActivity {

    //WebView
    @BindView(R.id.wv_web)
    WebView mWebView;
    //USB读卡
    private UsbManager mUsbManager;
    private static final String DEVICE_USB = "com.android.example.USB";
    public static ReaderAndroidUsb mReaderAndroidUsb = null;
    public static Reader mReader = null;

    @Override
    protected int getLayoutId() {
        return R.layout.act_main_new;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        //初始化WebView
        initWebView();
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        //WebSettings
        WebSettings webSettings = mWebView.getSettings();
        //设置WebView是否允许执行JavaScript脚本，默认false，不允许
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);      //将图片调整到适合WebView的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //设置脚本是否允许自动打开弹窗，默认false，不允许
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        webSettings.setDomStorageEnabled(true);
        //设置页面缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置WebView底层的布局算法
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        //设置允许混合模式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //设置JSBridge
        //mWebView.addJavascriptInterface(new JSBridge(this), "yinqingli");
        //处理各种通知 & 请求事件
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    return false;
                } else {
                    if (StringUtils.equals("yjylaio://main", url)) {
                        connectDevice();
                        new XPopup.Builder(mContext)
                                .dismissOnBackPressed(false) // 按返回键是否关闭弹窗，默认为true
                                .dismissOnTouchOutside(false) // 点击外部是否关闭弹窗，默认为true
                                .asCustom(new SwipePromptPopupView(mContext))
                                .show();
                        return true;
                    }
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
        });
    }


    @Override
    protected void doBusiness() {
        //加载网页
        mWebView.loadUrl(Constants.URL_MAIN);
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

    /**
     * 返回键处理
     */
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 避免WebView内存泄露
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
