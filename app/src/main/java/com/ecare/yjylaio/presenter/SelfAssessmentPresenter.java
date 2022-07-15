package com.ecare.yjylaio.presenter;

import com.blankj.utilcode.util.EncodeUtils;
import com.ecare.yjylaio.base.RxPresenter;
import com.ecare.yjylaio.contract.SelfAssessmentContract;
import com.ecare.yjylaio.model.api.ProjectApi;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.req.InsSelfAssessmentReqDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.model.rxjava.RxUtils;

/**
 * NeighborhoodLongevity
 * <p>
 * Created by xuminmin on 11/29/21.
 * Email: iminminxu@gmail.com
 */
public class SelfAssessmentPresenter extends RxPresenter<SelfAssessmentContract.View> implements SelfAssessmentContract.Presenter {

    /**
     * 提交能力自评
     *
     * @param q1 第一题
     * @param q2 第二题
     * @param q3 第三题
     * @param q4 第四题
     * @param q5 第五题
     * @param q6 第六题
     */
    @Override
    public void selfAssessmentSubmit(String iDCard, int q1, int q2, int q3, int q4, int q5, int q6) {
        if (!isAttachView()) {
            return;
        }
        mView.showLoadingDialog();
        addSubscribe(ProjectApi.getInstance().getApiService()
                .selfAssessmentSubmit(new InsSelfAssessmentReqDTO(new String(EncodeUtils.base64Decode(iDCard)), q1, q2, q3, q4, q5, q6))
                .compose(RxUtils.<BaseResponse<Boolean>>rxSchedulerHelper())
                .compose(RxUtils.<Boolean>handleResult())
                .subscribeWith(new CommonSubscriber<Boolean>(mView, CommonSubscriber.SHOW_LOADING_DIALOG) {
                    @Override
                    public void onNext(Boolean data) {
                        if (isAttachView()) {
                            mView.selfAssessmentSubmitSuc();
                        }
                    }
                })
        );
    }
}
