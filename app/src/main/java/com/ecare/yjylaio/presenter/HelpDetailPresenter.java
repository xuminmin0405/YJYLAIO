package com.ecare.yjylaio.presenter;

import com.ecare.yjylaio.base.RxPresenter;
import com.ecare.yjylaio.contract.HelpDetailContract;
import com.ecare.yjylaio.model.api.ProjectApi;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.req.JoinrActivityReqDTO;
import com.ecare.yjylaio.model.bean.rsp.VolunteerActivityRspDTO;
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
public class HelpDetailPresenter extends RxPresenter<HelpDetailContract.View> implements HelpDetailContract.Presenter {

    /**
     * 获取标签详情数据
     *
     * @param showType 显示类型
     * @param id       id
     */
    @Override
    public void getHelpDetail(final int showType, String id) {
        addSubscribe(ProjectApi.getInstance().getApiService()
                .getHelpDetail(id, "d7758732-b4bc-4888-aac1-1430e8d0d994")
                .compose(RxUtils.<BaseResponse<VolunteerActivityRspDTO>>rxSchedulerHelper())
                .compose(RxUtils.<VolunteerActivityRspDTO>handleResult())
                .subscribeWith(new CommonSubscriber<VolunteerActivityRspDTO>(mView, showType) {
                    @Override
                    public void onNext(VolunteerActivityRspDTO volunteerActivityRspDTO) {
                        if (isAttachView()) {
                            if (showType == CommonSubscriber.SHOW_STATE) {
                                mView.stateMain();
                            }
                            mView.setHelpDetail(volunteerActivityRspDTO);
                        }
                    }
                })
        );
    }

    @Override
    public void joinVolunteerActivity(String activityId) {
        if (!isAttachView()) {
            return;
        }
        mView.showLoadingDialog();
        addSubscribe(ProjectApi.getInstance().getApiService()
                .joinVolunteerActivity(new JoinrActivityReqDTO(activityId, "d7758732-b4bc-4888-aac1-1430e8d0d994"))
                .compose(RxUtils.<BaseResponse<Boolean>>rxSchedulerHelper())
                .compose(RxUtils.<Boolean>handleResult())
                .subscribeWith(new CommonSubscriber<Boolean>(mView, CommonSubscriber.SHOW_LOADING_DIALOG) {
                    @Override
                    public void onNext(Boolean data) {
                        if (isAttachView()) {
                            mView.joinVolunteerActivitySuc();
                        }
                    }
                })
        );
    }
}
