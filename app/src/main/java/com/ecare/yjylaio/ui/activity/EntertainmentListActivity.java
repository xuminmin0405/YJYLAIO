package com.ecare.yjylaio.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.BasePagingPresenter;
import com.ecare.yjylaio.base.BasePagingView;
import com.ecare.yjylaio.base.RootActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.presenter.EntertainmentListPresenter;
import com.ecare.yjylaio.widght.HorizontalDividerItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: EntertainmentListActivity
 * Description:
 * Author:
 * CreateDate: 2021/6/19 14:29
 * Version: 1.0
 */
public class EntertainmentListActivity extends RootActivity<BasePagingPresenter> implements BasePagingView {

    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //加载组件
    @BindView(R.id.view_main)
    SmartRefreshLayout srlRefresh;
    //视频列表
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @Override
    protected BasePagingPresenter createPresenter() {
        return new EntertainmentListPresenter(getIntent().getStringExtra(Constants.IT_CATEGORY));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_entertainment_list;
    }


    @Override
    protected void initVariables() {

    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        //设置标题
        tvTitle.setText(getIntent().getStringExtra(Constants.IT_TITLE));
        //初始化SmartRefreshLayout
        initSmartRefreshLayout();
        //初始化视频列表
        initRecyclerView();
    }

    /**
     * 初始化SmartRefreshLayout
     */
    private void initSmartRefreshLayout() {
        srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMoreData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refreshData();
            }
        });
    }

    /**
     * 初始化视频列表
     */
    private void initRecyclerView() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        BaseQuickAdapter adapter = mPresenter.getAdapter();
        rvList.setAdapter(adapter);
        adapter.setEmptyView(R.layout.view_empty);
        rvList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .color(getResources().getColor(R.color.colorE6E6E6))
                .sizeResId(R.dimen.dp_1)
                .build());
    }

    /**
     * 点击事件
     *
     * @param view view
     */
    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void doBusiness() {
        mPresenter.firstLoadData();
    }

    @Override
    public void setNoMoreData() {
        srlRefresh.setNoMoreData(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter != null) {
            mPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }
}
