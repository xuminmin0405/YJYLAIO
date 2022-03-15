package com.ecare.yjylaio.base;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/3.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * BasePresenter
 */
public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void detachView();

    boolean isAttachView();
}