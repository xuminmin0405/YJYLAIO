package com.ecare.yjylaio.presenter;

import com.ecare.yjylaio.base.RxPresenter;
import com.ecare.yjylaio.contract.HelpLabelDetailContract;
import com.ecare.yjylaio.model.api.ProjectApi;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.req.AcceptLableReqDTO;
import com.ecare.yjylaio.model.bean.rsp.LableRspDTO;
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
public class HelpLabelDetailPresenter extends RxPresenter<HelpLabelDetailContract.View> implements HelpLabelDetailContract.Presenter {

    /**
     * 获取标签详情数据
     *
     * @param showType 显示类型
     * @param id       id
     */
    @Override
    public void getLabelDetail(final int showType, String id) {
        addSubscribe(ProjectApi.getInstance().getApiService()
                .getLabelDetail(id, "d7758732-b4bc-4888-aac1-1430e8d0d994")
                .compose(RxUtils.<BaseResponse<LableRspDTO>>rxSchedulerHelper())
                .compose(RxUtils.<LableRspDTO>handleResult())
                .subscribeWith(new CommonSubscriber<LableRspDTO>(mView, showType) {
                    @Override
                    public void onNext(LableRspDTO lableRspDTO) {
                        if (isAttachView()) {
                            if (showType == CommonSubscriber.SHOW_STATE) {
                                mView.stateMain();
                            }
                            mView.setLabelDetail(lableRspDTO);
                        }
                    }
                })
        );
    }

    @Override
    public void acceptLabel(String labelId) {
        if (!isAttachView()) {
            return;
        }
        mView.showLoadingDialog();
        addSubscribe(ProjectApi.getInstance().getApiService()
                .acceptLabel(new AcceptLableReqDTO(labelId, "d7758732-b4bc-4888-aac1-1430e8d0d994"))
                .compose(RxUtils.<BaseResponse<Boolean>>rxSchedulerHelper())
                .compose(RxUtils.<Boolean>handleResult())
                .subscribeWith(new CommonSubscriber<Boolean>(mView, CommonSubscriber.SHOW_LOADING_DIALOG) {
                    @Override
                    public void onNext(Boolean data) {
                        if (isAttachView()) {
                            mView.acceptLabelSuc();
                        }
                    }
                })
        );
    }
}
