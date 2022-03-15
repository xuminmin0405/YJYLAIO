package com.ecare.yjylaio.base;

import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;

import java.util.List;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/11.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * PagingPresenter
 */
public abstract class PagingPresenter<T extends BasePagingView, E> extends RxPresenter<T> implements BasePagingPresenter<T, E> {

    //当前分页页码
    protected int mPageIndex;
    //分页加载数量
    protected int mPageSize = getPageSize();
    //Adapter
    protected BaseQuickAdapter<E, BaseViewHolder> mAdapter;

    /**
     * 此方法返回分页初始页码，默认使用Constants.PAGE_INDEX，如需特殊分页初始页码，重写此方法
     *
     * @return 分页初始页码
     */
    protected int getInitialPageIndex() {
        return Constants.PAGE_INDEX;
    }

    /**
     * 此方法返回分页加载数量，默认使用Constants.PAGE_SIZE，如需特殊分页加载数量，重写此方法
     *
     * @return 分页加载数量
     */
    protected int getPageSize() {
        return Constants.PAGE_SIZE;
    }

    /**
     * 创建Adapter
     *
     * @return Adapter
     */
    protected abstract BaseQuickAdapter<E, BaseViewHolder> createAdapter();

    /**
     * 加载分页数据
     *
     * @param showType  加载分页数据显示的类型
     * @param pageIndex 加载分页数据的页码
     */
    protected abstract void loadData(@CommonSubscriber.ShowType int showType, int pageIndex);

    /**
     * 设置分页数据
     *
     * @param showType  加载分页数据显示的类型
     * @param pageIndex 加载分页数据的页码
     * @param data      分页数据
     */
    protected void setData(@CommonSubscriber.ShowType int showType, int pageIndex, List<E> data) {
        if (showType == CommonSubscriber.SHOW_STATE) {
            mView.stateMain();
        }
        if (mAdapter == null) {
            throw new IllegalStateException(
                    "The subclass of PagingPresenter must create a non null adapter.");
        }
        this.mPageIndex = pageIndex;
        if (mPageIndex == getInitialPageIndex()) {
            mAdapter.setNewData(data);
        } else {
            mAdapter.addData(data);
        }
        if (data == null || data.size() < mPageSize) {
            mView.setNoMoreData();
        }
    }

    @Override
    public BaseQuickAdapter<E, BaseViewHolder> getAdapter() {
        if (mAdapter == null) {
            mAdapter = createAdapter();
        }
        return mAdapter;
    }

    @Override
    public void firstLoadData() {
        mView.stateLoading();
        loadData(CommonSubscriber.SHOW_STATE, getInitialPageIndex());
    }

    @Override
    public void refreshData() {
        loadData(CommonSubscriber.SHOW_REFRESH_LAYOUT, getInitialPageIndex());
    }

    @Override
    public void loadMoreData() {
        loadData(CommonSubscriber.SHOW_REFRESH_LAYOUT, mPageIndex + 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}