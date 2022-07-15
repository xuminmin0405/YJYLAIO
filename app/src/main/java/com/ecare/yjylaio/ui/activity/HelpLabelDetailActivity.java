package com.ecare.yjylaio.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.RootActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.contract.HelpLabelDetailContract;
import com.ecare.yjylaio.model.bean.rsp.LableRspDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.presenter.HelpLabelDetailPresenter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: YJYLAged
 * Package: com.ecare.yjylaged.ui.activity
 * ClassName: HelpLabelDetailActivity
 * Description:
 * Author:
 * CreateDate: 2021/8/23 14:40
 * Version: 1.0
 */
public class HelpLabelDetailActivity extends RootActivity<HelpLabelDetailContract.Presenter> implements HelpLabelDetailContract.View {

    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //加载组件
    @BindView(R.id.view_main)
    SmartRefreshLayout srlRefresh;
    //标签封面
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    //标签名
    @BindView(R.id.tv_name)
    TextView tvName;
    //WebView
    @BindView(R.id.wv_web)
    WebView mWebView;
    //提交
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    //活动id
    private String mId;
    //活动详情
    private LableRspDTO mData;

    @Override
    protected HelpLabelDetailContract.Presenter createPresenter() {
        return new HelpLabelDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_help_label_detail;
    }

    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mId = getIntent().getStringExtra(Constants.IT_ID);
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        //设置标题
        tvTitle.setText("标签详情");
        //初始化SmartRefreshLayout
        initSmartRefreshLayout();
        //初始化WebView
        initWebView();
    }

    /**
     * 初始化SmartRefreshLayout
     */
    private void initSmartRefreshLayout() {
        srlRefresh.setEnableLoadMore(false);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getLabelDetail(CommonSubscriber.SHOW_REFRESH_LAYOUT, mId);
            }
        });
    }

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
    }

    /**
     * 点击事件
     *
     * @param view view
     */
    @OnClick({R.id.iv_back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:
                if (mData == null || mData.getIsApply() == 1) {
                    break;
                }
                mPresenter.acceptLabel(mData.getId());
                break;
            default:
                break;
        }
    }

    @Override
    protected void doBusiness() {
        tvSubmit.setVisibility(View.GONE);
        stateLoading();
        mPresenter.getLabelDetail(CommonSubscriber.SHOW_STATE, mId);
    }

    /**
     * 设置标签详情数据
     *
     * @param lableRspDTO
     */
    @Override
    public void setLabelDetail(LableRspDTO lableRspDTO) {
        tvSubmit.setVisibility(View.VISIBLE);
        if (lableRspDTO == null) {
            return;
        }
        this.mData = lableRspDTO;
        tvTitle.setText(lableRspDTO.getLableName());
        Glide.with(mContext).load(lableRspDTO.getImg()).into(ivCover);
        tvName.setText(lableRspDTO.getLableName());
        //加载网页
        mWebView.loadDataWithBaseURL(null, getHtmlData(), "text/html", "utf-8", null);
        if (lableRspDTO.getIsApply() != 1) {
            tvSubmit.setText("提交申请");
        } else {
            String applyStatus = lableRspDTO.getApplyStatus();
            if (StringUtils.equals(applyStatus, "0")) {
                tvSubmit.setText("待审核");
            } else if (StringUtils.equals(applyStatus, "1")) {
                tvSubmit.setText("审核未通过");
            } else if (StringUtils.equals(applyStatus, "2")) {
                tvSubmit.setText("审核通过");
            }
        }
    }

    private String getHtmlData() {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + mData.getRequirement() + "</body></html>";
    }

    @Override
    public void acceptLabelSuc() {
        showMsg("提交申请成功");
        srlRefresh.autoRefresh();
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
