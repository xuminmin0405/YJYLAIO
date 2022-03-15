package com.ecare.yjylaio.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.BasePagingView;
import com.ecare.yjylaio.base.PagingPresenter;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.model.api.ProjectApi;
import com.ecare.yjylaio.model.bean.BasePaging;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.rsp.TrainingVideoRspDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.model.rxjava.RxUtils;
import com.ecare.yjylaio.ui.activity.WebPageActivity;

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
public class CheatedListPresenter extends PagingPresenter<BasePagingView, TrainingVideoRspDTO> {

    //类别
    private String mCategory;
    //上下文
    private Context mContext;

    public CheatedListPresenter(String category) {
        this.mCategory = category;
    }

    @Override
    protected BaseQuickAdapter<TrainingVideoRspDTO, BaseViewHolder> createAdapter() {
        BaseQuickAdapter<TrainingVideoRspDTO, BaseViewHolder> adapter = new BaseQuickAdapter<TrainingVideoRspDTO, BaseViewHolder>(R.layout.item_cheated) {
            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                CheatedListPresenter.this.mContext = parent.getContext();
                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            protected void convert(BaseViewHolder helper, TrainingVideoRspDTO item) {
                if (item == null) {
                    return;
                }
                Glide.with(mContext).load(item.getImgUrl()).into((ImageView) helper.getView(R.id.iv_cover));
                helper.setText(R.id.tv_title, item.getName());
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (!isAttachView() || mAdapter.getItem(position) == null) {
                    return;
                }
                Intent intent = new Intent(mContext, WebPageActivity.class);
                intent.putExtra(Constants.IT_TITLE, "视频播放");
                intent.putExtra(Constants.IT_URL, mAdapter.getItem(position).getUrl());
                mView.startActivityForResult(intent, -1);
            }
        });
        return adapter;
    }

    @Override
    protected void loadData(final int showType, final int pageIndex) {
        addSubscribe(ProjectApi.getInstance().getApiService()
                .getTrainingVideo(mCategory, "", pageIndex, mPageSize)
                .compose(RxUtils.<BaseResponse<BasePaging<TrainingVideoRspDTO>>>rxSchedulerHelper())
                .compose(RxUtils.<BasePaging<TrainingVideoRspDTO>>handleResult())
                .subscribeWith(new CommonSubscriber<BasePaging<TrainingVideoRspDTO>>(mView, showType) {
                    @Override
                    public void onNext(BasePaging<TrainingVideoRspDTO> data) {
                        if (isAttachView()) {
                            setData(showType, pageIndex, data.getList());
                        }
                    }
                })
        );
    }
}
