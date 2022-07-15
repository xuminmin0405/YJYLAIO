package com.ecare.yjylaio.presenter;

import com.ecare.yjylaio.base.RxPresenter;
import com.ecare.yjylaio.contract.MainContract;
import com.ecare.yjylaio.model.api.RTCApi;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.model.rxjava.RxUtils;

/**
 * NeighborhoodLongevity
 * <p>
 * Created by xuminmin on 11/29/21.
 * Email: iminminxu@gmail.com
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    public void getEnteredTotal() {
        addSubscribe(RTCApi.getInstance().getApiService()
                .getEnteredTotal()
                .compose(RxUtils.<BaseResponse<Integer>>rxSchedulerHelper())
                .compose(RxUtils.<Integer>handleResult())
                .subscribeWith(new CommonSubscriber<Integer>(mView) {
                    @Override
                    public void onNext(Integer data) {
                        if (isAttachView()) {
                            mView.setEnteredTotal(data);
                        }
                    }
                })
        );
    }
}
