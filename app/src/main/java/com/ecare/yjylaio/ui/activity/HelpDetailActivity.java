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

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.RootActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.contract.HelpDetailContract;
import com.ecare.yjylaio.model.bean.rsp.VolunteerActivityRspDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.presenter.HelpDetailPresenter;
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
public class HelpDetailActivity extends RootActivity<HelpDetailContract.Presenter> implements HelpDetailContract.View {

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
    //内容
    @BindView(R.id.tv_content)
    TextView tvContent;
    //WebView
    @BindView(R.id.wv_web)
    WebView mWebView;
    //提交
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    //活动id
    private String mId;
    //活动详情
    private VolunteerActivityRspDTO mData;

    @Override
    protected HelpDetailContract.Presenter createPresenter() {
        return new HelpDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_help_detail;
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
        tvTitle.setText("活动详情");
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
                mPresenter.getHelpDetail(CommonSubscriber.SHOW_REFRESH_LAYOUT, mId);
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
                if (mData == null || mData.getIsJoin() == 1 || mData.isIsComplete() || mData.getActivityStatus() != 1) {
                    break;
                }
                mPresenter.joinVolunteerActivity(mData.getId());
                break;
            default:
                break;
        }
    }

    @Override
    protected void doBusiness() {
        tvSubmit.setVisibility(View.GONE);
        stateLoading();
        mPresenter.getHelpDetail(CommonSubscriber.SHOW_STATE, mId);
    }

    /**
     * 设置标签详情数据
     *
     * @param volunteerActivityRspDTO
     */
    @Override
    public void setHelpDetail(VolunteerActivityRspDTO volunteerActivityRspDTO) {
        tvSubmit.setVisibility(View.VISIBLE);
        if (volunteerActivityRspDTO == null) {
            return;
        }
        this.mData = volunteerActivityRspDTO;
        tvTitle.setText(mData.getActivityName());
        Glide.with(mContext).load(mData.getActivityImg()).into(ivCover);
        tvName.setText(mData.getActivityName());
        tvContent.setText(new StringBuilder().append("报名情况：").append(mData.getJoinCount()).append("/").append(mData.getTotalCount()).append("\n")
                .append("报名日期：").append(TimeUtils.date2String(mData.getStartSignTime(), TimeUtils.getSafeDateFormat("yyyy-MM-dd"))).append("~").append(TimeUtils.date2String(mData.getEndSignTime(), TimeUtils.getSafeDateFormat("yyyy-MM-dd"))).append("\n")
                .append("活动时间：").append(TimeUtils.date2String(mData.getActivityTime(), TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm"))).append("\n")
                .append("活动地点：").append(mData.getActivityAddress()));
        //加载网页
        mWebView.loadDataWithBaseURL(null, getHtmlData(), "text/html", "utf-8", null);
        if (mData.getIsJoin() == 1) {
            tvSubmit.setText("已报名");
        } else if (mData.isIsComplete()) {
            tvSubmit.setText("人数已满");
        } else {
            int activityStatus = mData.getActivityStatus();
            if (activityStatus == 0) {
                tvSubmit.setText("即将开放报名");
            } else if (activityStatus == 1) {
                tvSubmit.setText("立即报名");
            } else if (activityStatus == 2) {
                tvSubmit.setText("活动报名结束");
            } else if (activityStatus == 3) {
                tvSubmit.setText("活动进行中");
            } else if (activityStatus == 4) {
                tvSubmit.setText("活动已结束");
            }
        }
    }

    private String getHtmlData() {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + mData.getActivityContent() + "</body></html>";
    }

    @Override
    public void joinVolunteerActivitySuc() {
        showMsg("报名成功");
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
