package com.ecare.yjylaio.contract;

import com.ecare.yjylaio.base.BasePresenter;
import com.ecare.yjylaio.base.BaseView;

/**
 * NeighborhoodLongevity
 * <p>
 * Created by xuminmin on 12/8/21.
 * Email: iminminxu@gmail.com
 */
public interface SelfAssessmentContract {

    interface View extends BaseView {

        void selfAssessmentSubmitSuc();
    }

    interface Presenter extends BasePresenter<View> {

        void selfAssessmentSubmit(String iDCard, int q1, int q2, int q3, int q4, int q5, int q6);
    }
}
