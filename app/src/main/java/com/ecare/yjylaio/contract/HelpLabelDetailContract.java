package com.ecare.yjylaio.contract;

import com.ecare.yjylaio.base.BasePresenter;
import com.ecare.yjylaio.base.BaseView;
import com.ecare.yjylaio.model.bean.rsp.LableRspDTO;
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
public interface HelpLabelDetailContract {

    interface View extends BaseView {

        void setLabelDetail(LableRspDTO lableRspDTO);

        void acceptLabelSuc();
    }

    interface Presenter extends BasePresenter<View> {

        void getLabelDetail(@CommonSubscriber.ShowType int showType, String id);

        void acceptLabel(String labelId);
    }
}
