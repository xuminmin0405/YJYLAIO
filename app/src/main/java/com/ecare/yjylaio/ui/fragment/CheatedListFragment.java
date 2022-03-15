package com.ecare.yjylaio.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.BasePagingPresenter;
import com.ecare.yjylaio.base.BasePagingView;
import com.ecare.yjylaio.base.RootFragment;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.presenter.CheatedListPresenter;
import com.ecare.yjylaio.widght.GridSpacingItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.fragment
 * ClassName: CheatedListFragment
 * Description:
 * Author:
 * CreateDate: 2021/6/18 13:49
 * Version: 1.0
 */
public class CheatedListFragment extends RootFragment<BasePagingPresenter> implements BasePagingView {

    //加载组件
    @BindView(R.id.view_main)
    SmartRefreshLayout srlRefresh;
    //视频列表
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    /**
     * CheatedListFragment构建方法
     *
     * @param category 类别
     * @return CheatedListFragment
     */
    public static CheatedListFragment newInstance(String category) {
        CheatedListFragment fragment = new CheatedListFragment();
        Bundle args = new Bundle();
        args.putString(Constants.IT_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePagingPresenter createPresenter() {
        return new CheatedListPresenter(getArguments().getString(Constants.IT_CATEGORY));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_cheated_list;
    }


    @Override
    protected void initVariables() {

    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
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
        rvList.setLayoutManager(new GridLayoutManager(mContext, 2));
        BaseQuickAdapter adapter = mPresenter.getAdapter();
        rvList.setAdapter(adapter);
        adapter.setEmptyView(R.layout.view_empty);
        rvList.addItemDecoration(new GridSpacingItemDecoration(2, (int) getResources().getDimension(R.dimen.dp_10), true));
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
