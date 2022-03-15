package com.ecare.yjylaio.contract;

import com.ecare.yjylaio.base.BasePresenter;
import com.ecare.yjylaio.base.BaseView;
import com.ecare.yjylaio.model.bean.rsp.UserHealthDataRspDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.contract
 * ClassName: DoctorContract
 * Description:
 * Author:
 * CreateDate: 2021/5/2 10:13
 * Version: 1.0
 */
public interface DoctorContract {

    interface View extends BaseView {

        void setHealthData(UserHealthDataRspDTO userHealthData);

        void setYTJDevice(String device);
    }

    interface Presenter extends BasePresenter<View> {

        void getHealthData(@CommonSubscriber.ShowType int showType, String idCard);

        void getYTJDevice();
    }
}
