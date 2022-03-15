package com.ecare.yjylaio.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/11.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * SimpleFragment
 */
public abstract class SimpleFragment extends Fragment {

    //Context实例
    protected Context mContext;
    //mRootView
    protected View mRootView;
    //UnBinder
    private Unbinder mUnBinder;
    //懒加载
    private boolean isViewCreated;   //界面是否已创建完成
    private boolean isVisibleToUser; //是否对用户可见
    private boolean isDataLoaded;    //数据是否已请求

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //设置Context实例
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mRootView == null ? inflater.inflate(getLayoutId(), container, false) : mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mRootView == null) {
            mRootView = view;
            //绑定到ButterKnife
            mUnBinder = ButterKnife.bind(this, view);
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
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        tryLoadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        isViewCreated = true;
        tryLoadData();
    }

    public void tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && !isDataLoaded) {
            //业务操作
            doBusiness();
            isDataLoaded = true;
            //通知子Fragment请求数据
            dispatchParentVisibleState();
        }
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || (fragment instanceof SimpleFragment && ((SimpleFragment) fragment).isVisibleToUser);
    }

    /**
     * ViewPager场景下，当前fragment可见时，如果其子fragment也可见，则让子fragment请求数据
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof SimpleFragment && ((SimpleFragment) child).isVisibleToUser) {
                ((SimpleFragment) child).tryLoadData();
            }
        }
    }

    @Override
    public void onDestroy() {
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
