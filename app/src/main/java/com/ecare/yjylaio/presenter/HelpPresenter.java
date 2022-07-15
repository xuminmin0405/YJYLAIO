package com.ecare.yjylaio.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.PagingPresenter;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.contract.HelpContract;
import com.ecare.yjylaio.model.api.ProjectApi;
import com.ecare.yjylaio.model.bean.BasePaging;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.req.VolunteerActivityReqDTO;
import com.ecare.yjylaio.model.bean.rsp.LableRspDTO;
import com.ecare.yjylaio.model.bean.rsp.VolunteerActivityRspDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.model.rxjava.RxUtils;
import com.ecare.yjylaio.ui.activity.HelpDetailActivity;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.presenter
 * ClassName: CheatedListPresenter
 * Description:
 * Author:
 * CreateDate: 2021/5/2 14:43
 * Version: 1.0
 */
public class HelpPresenter extends PagingPresenter<HelpContract.View, VolunteerActivityRspDTO> implements HelpContract.Presenter {

    //上下文
    private Context mContext;

    public HelpPresenter() {

    }

    @Override
    protected BaseQuickAdapter<VolunteerActivityRspDTO, BaseViewHolder> createAdapter() {
        BaseQuickAdapter<VolunteerActivityRspDTO, BaseViewHolder> adapter = new BaseQuickAdapter<VolunteerActivityRspDTO, BaseViewHolder>(R.layout.item_help_activity) {
            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                HelpPresenter.this.mContext = parent.getContext();
                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            protected void convert(BaseViewHolder helper, VolunteerActivityRspDTO item) {
                if (item == null) {
                    return;
                }
                Glide.with(mContext).load(item.getActivityImg())
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.dp_5))))
                        .into((ImageView) helper.getView(R.id.iv_cover));
                helper.setText(R.id.tv_title, item.getActivityName())
                        .setText(R.id.tv_content, "已报名：" + item.getJoinCount() + "人");
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (!isAttachView() || mAdapter.getItem(position) == null) {
                    return;
                }
                Intent intent = new Intent(mContext, HelpDetailActivity.class);
                intent.putExtra(Constants.IT_ID, mAdapter.getItem(position).getId());
                mContext.startActivity(intent);
            }
        });
        return adapter;
    }

    @Override
    protected void loadData(final int showType, final int pageIndex) {
        addSubscribe(ProjectApi.getInstance().getApiService()
                .getActivity(new VolunteerActivityReqDTO("1", pageIndex, mPageSize, "4032"))
                .compose(RxUtils.<BaseResponse<BasePaging<VolunteerActivityRspDTO>>>rxSchedulerHelper())
                .compose(RxUtils.<BasePaging<VolunteerActivityRspDTO>>handleResult())
                .subscribeWith(new CommonSubscriber<BasePaging<VolunteerActivityRspDTO>>(mView, showType) {
                    @Override
                    public void onNext(BasePaging<VolunteerActivityRspDTO> data) {
                        if (isAttachView()) {
                            setData(showType, pageIndex, data.getList());
                        }
                    }
                })
        );
    }

    @Override
    public void getLabel() {
        addSubscribe(ProjectApi.getInstance().getApiService()
                .getLabel()
                .compose(RxUtils.<BaseResponse<List<LableRspDTO>>>rxSchedulerHelper())
                .compose(RxUtils.<List<LableRspDTO>>handleResult())
                .subscribeWith(new CommonSubscriber<List<LableRspDTO>>(mView) {
                    @Override
                    public void onNext(List<LableRspDTO> data) {
                        if (isAttachView()) {
                            mView.setLabel(data);
                        }
                    }
                })
        );
    }
}
