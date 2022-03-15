package com.ecare.yjylaio.base;

import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/3.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * BasePagingPresenter
 */
public interface BasePagingPresenter<T extends BasePagingView, E> extends BasePresenter<T> {

    BaseQuickAdapter<E, BaseViewHolder> getAdapter();

    void firstLoadData();

    void refreshData();

    void loadMoreData();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}