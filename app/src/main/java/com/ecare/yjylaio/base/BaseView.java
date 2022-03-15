package com.ecare.yjylaio.base;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/3.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * BaseView
 */
public interface BaseView {

    void showMsg(String msg);

    void logout();

    //========== LoadingDialog ==========

    void showLoadingDialog();

    void hideLoadingDialog();

    //========== State ==========

    void stateLoading();

    void stateMain();

    void stateError();

    //========== SmartRefreshLayout ==========

    void finishRefreshOrLoadMore();
}