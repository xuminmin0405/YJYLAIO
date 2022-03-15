package com.ecare.yjylaio.model.rxjava;

import com.ecare.yjylaio.base.BaseView;
import com.ecare.yjylaio.model.exception.ApiException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import androidx.annotation.IntDef;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/11.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * CommonSubscriber
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

    public static final int SHOW_LOADING_DIALOG = 1;
    public static final int SHOW_STATE = 2;
    public static final int SHOW_REFRESH_LAYOUT = 3;

    @IntDef({SHOW_LOADING_DIALOG, SHOW_STATE, SHOW_REFRESH_LAYOUT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowType {

    }

    private BaseView mView;
    private int mShowType;

    protected CommonSubscriber() {

    }

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, @ShowType int showType) {
        this.mView = view;
        this.mShowType = showType;
    }

    @Override
    public void onComplete() {
        if (mView == null) {
            return;
        }
        if (mShowType == SHOW_LOADING_DIALOG) {
            mView.hideLoadingDialog();
        } else if (mShowType == SHOW_REFRESH_LAYOUT) {
            mView.finishRefreshOrLoadMore();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        //显示错误信息（SHOW_STATE页面显示，其他吐司显示）
        String errorMsg;
        if (e instanceof HttpException || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            errorMsg = "网络异常，请稍后重试";
        } else {
            errorMsg = e.getMessage();
        }
        if (mShowType == SHOW_STATE) {
            // TODO: 2021/3/2 此处可以传入错误信息，在错误页面显示
            mView.stateError();
        } else {
            mView.showMsg(errorMsg);
        }
        if (mShowType == SHOW_LOADING_DIALOG) {
            mView.hideLoadingDialog();
        } else if (mShowType == SHOW_REFRESH_LAYOUT) {
            mView.finishRefreshOrLoadMore();
        }
        if (e instanceof ApiException) {
            disposeApiException((ApiException) e);
        }
    }

    /**
     * 处理服务器返回错误
     *
     * @param e ApiException
     */
    private void disposeApiException(ApiException e) {
        switch (e.getErrorNumber()) {
            default:
                break;
        }
    }
}
