package com.ecare.yjylaio.base;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/11.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * SimpleActivity
 */
public abstract class SimpleActivity extends AppCompatActivity {

    //Activity实例
    protected AppCompatActivity mContext;
    //UnBinder
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(getLayoutId());
        //设置Activity实例
        mContext = this;
        //绑定到ButterKnife
        mUnBinder = ButterKnife.bind(this);
        //根据设置判断是否注册EventBus(默认不注册)
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        //初始化Presenter
        initPresenter();
        //初始化变量
        initVariables();
        //初始化View
        initViews(savedInstanceState);
        //业务操作
        doBusiness();
    }

    @Override
    protected void onDestroy() {
        mUnBinder.unbind();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 获取布局Id
     *
     * @return 布局Id
     */
    protected abstract int getLayoutId();

    /**
     * 使用EventBus
     *
     * @return 默认不注册
     */
    protected boolean useEventBus() {
        return false;
    }

    /**
     * 初始化Presenter
     */
    protected void initPresenter() {

    }

    /**
     * 初始化变量
     */
    protected abstract void initVariables();

    /**
     * 初始化View
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    /**
     * 业务操作
     */
    protected abstract void doBusiness();
}