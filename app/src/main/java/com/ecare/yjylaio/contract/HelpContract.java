package com.ecare.yjylaio.contract;


import com.ecare.yjylaio.base.BasePagingPresenter;
import com.ecare.yjylaio.base.BasePagingView;
import com.ecare.yjylaio.model.bean.rsp.LableRspDTO;
import com.ecare.yjylaio.model.bean.rsp.VolunteerActivityRspDTO;

import java.util.List;

/**
 * ProjectName: YJYLMerchants
 * Package: com.ecare.yjylmerchants.contract
 * ClassName: LoginContract
 * Description:
 * Author:
 * CreateDate: 2021/5/2 10:13
 * Version: 1.0
 */
public interface HelpContract {

    interface View extends BasePagingView {

        void setLabel(List<LableRspDTO> data);
    }

    interface Presenter extends BasePagingPresenter<View, VolunteerActivityRspDTO> {

        void getLabel();
    }
}
