package com.ecare.yjylaio.base;

import android.content.Intent;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/3.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * BasePagingView
 */
public interface BasePagingView extends BaseView {

    void setNoMoreData();

    void startActivityForResult(Intent intent, int requestCode);
}