package com.ecare.yjylaio.presenter;

import com.ecare.yjylaio.base.RxPresenter;
import com.ecare.yjylaio.contract.DoctorContract;
import com.ecare.yjylaio.model.api.ProjectApi;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.rsp.UserHealthDataRspDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.model.rxjava.RxUtils;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.presenter
 * ClassName: DoctorPresenter
 * Description:
 * Author:
 * CreateDate: 2021/5/2 10:13
 * Version: 1.0
 */
public class DoctorPresenter extends RxPresenter<DoctorContract.View> implements DoctorContract.Presenter {

    /**
     * 获取单人健康数据
     *
     * @param showType 显示类型
     * @param idCard   身份证号
     */
    @Override
    public void getHealthData(final int showType, String idCard) {
        addSubscribe(ProjectApi.getInstance().getApiService()
                .getHealthData(idCard)
                .compose(RxUtils.<BaseResponse<UserHealthDataRspDTO>>rxSchedulerHelper())
                .compose(RxUtils.<UserHealthDataRspDTO>handleResult())
                .subscribeWith(new CommonSubscriber<UserHealthDataRspDTO>(mView, showType) {
                    @Override
                    public void onNext(UserHealthDataRspDTO userHealthData) {
                        if (isAttachView()) {
                            if (showType == CommonSubscriber.SHOW_STATE) {
                                mView.stateMain();
                            }
                            mView.setHealthData(userHealthData);
                        }
                    }
                })
        );
    }

    /**
     * 获取乐橙设备数据
     */
    @Override
    public void getYTJDevice() {
        if (!isAttachView()) {
            return;
        }
        addSubscribe(ProjectApi.getInstance().getApiService()
                .getYTJDevice()
                .compose(RxUtils.<BaseResponse<String>>rxSchedulerHelper())
                .compose(RxUtils.<String>handleResult())
                .subscribeWith(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String data) {
                        if (isAttachView()) {
                            mView.setYTJDevice(data);
                        }
                    }
                })
        );
    }
}
