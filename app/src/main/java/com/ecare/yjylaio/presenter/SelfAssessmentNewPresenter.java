package com.ecare.yjylaio.presenter;

import com.ecare.yjylaio.base.RxPresenter;
import com.ecare.yjylaio.contract.SelfAssessmentNewContract;
import com.ecare.yjylaio.model.api.TransApi;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.rsp.SelfAssessmentRspDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.model.rxjava.RxUtils;

/**
 * NeighborhoodLongevity
 * <p>
 * Created by xuminmin on 11/29/21.
 * Email: iminminxu@gmail.com
 */
public class SelfAssessmentNewPresenter extends RxPresenter<SelfAssessmentNewContract.View> implements SelfAssessmentNewContract.Presenter {

    public void getSelfAssessment(String iDCard) {
        if (!isAttachView()) {
            return;
        }
        addSubscribe(TransApi.getInstance().getApiService()
                .getSelfAssessment(iDCard, "idCard", "1302", "1311113038715826176")
                .compose(RxUtils.<BaseResponse<SelfAssessmentRspDTO>>rxSchedulerHelper())
                .compose(RxUtils.<SelfAssessmentRspDTO>handleResult())
                .subscribeWith(new CommonSubscriber<SelfAssessmentRspDTO>(mView, CommonSubscriber.SHOW_LOADING_DIALOG) {
                    @Override
                    public void onNext(SelfAssessmentRspDTO data) {
                        if (isAttachView()) {
                            mView.setSelfAssessment(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (isAttachView()) {
                            mView.setSelfAssessment(null);
                        }
                    }
                })
        );
    }
}
