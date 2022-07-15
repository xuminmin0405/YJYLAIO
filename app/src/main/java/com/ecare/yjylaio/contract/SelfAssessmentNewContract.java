package com.ecare.yjylaio.contract;

import com.ecare.yjylaio.base.BasePresenter;
import com.ecare.yjylaio.base.BaseView;
import com.ecare.yjylaio.model.bean.rsp.SelfAssessmentRspDTO;

/**
 * NeighborhoodLongevity
 * <p>
 * Created by xuminmin on 12/8/21.
 * Email: iminminxu@gmail.com
 */
public interface SelfAssessmentNewContract {

    interface View extends BaseView {

        void setSelfAssessment(SelfAssessmentRspDTO selfAssessmentRspDTO);
    }

    interface Presenter extends BasePresenter<View> {

        void getSelfAssessment(String iDCard);
    }
}
