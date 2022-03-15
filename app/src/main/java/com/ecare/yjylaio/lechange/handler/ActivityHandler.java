package com.ecare.yjylaio.lechange.handler;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import androidx.fragment.app.FragmentActivity;

public abstract class ActivityHandler extends Handler {
        private WeakReference<FragmentActivity> mActivity;
        public ActivityHandler(FragmentActivity activity){
            super();
            this.mActivity = new WeakReference<FragmentActivity>(activity);
        }
        public abstract  void handleMsg(Message msg);
        @Override
        public void handleMessage(Message msg) {
            FragmentActivity activity = mActivity.get();
            if(activity == null){
                return;
            }
            handleMsg(msg);
            super.handleMessage(msg);
        }
    }