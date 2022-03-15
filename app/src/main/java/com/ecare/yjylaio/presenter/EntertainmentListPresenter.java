package com.ecare.yjylaio.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
public class EntertainmentListPresenter extends PagingPresenter<BasePagingView, TrainingVideoRspDTO> {

    //类别
    private String mCategory;
    //上下文
    private Context mContext;

    public EntertainmentListPresenter(String category) {
        this.mCategory = category;
    }

    @Override
    protected BaseQuickAdapter<TrainingVideoRspDTO, BaseViewHolder> createAdapter() {
        BaseQuickAdapter<TrainingVideoRspDTO, BaseViewHolder> adapter = new BaseQuickAdapter<TrainingVideoRspDTO, BaseViewHolder>(R.layout.item_entertainment) {
            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                EntertainmentListPresenter.this.mContext = parent.getContext();
                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            protected void convert(BaseViewHolder helper, TrainingVideoRspDTO item) {
                if (item == null) {
                    return;
                }
                Glide.with(mContext).load(item.getImgUrl())
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.dp_5))))
                        .into((ImageView) helper.getView(R.id.iv_cover));
                helper.setText(R.id.tv_title, item.getName())
                        .setText(R.id.tv_category, getCategoryNameForCategory(String.valueOf(item.getCategory())))
                        .setText(R.id.tv_duration, "视频时长：" + item.getDuration());
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

    private String getCategoryNameForCategory(String category) {
        if (StringUtils.isEmpty(category)) {
            return "未知类型";
        }
        switch (category) {
            case TrainingVideoRspDTO.CATEGORY_FITNESS:
                return "科学健身";
            case TrainingVideoRspDTO.CATEGORY_INVESTMENT:
                return "投资理财";
            case TrainingVideoRspDTO.CATEGORY_HEALTH_PRODUCTS:
                return "保健品";
            case TrainingVideoRspDTO.CATEGORY_OTHER:
                return "其他";
            case TrainingVideoRspDTO.CATEGORY_EDUCATION:
                return "教育课程";
            case TrainingVideoRspDTO.CATEGORY_MEDICAL:
                return "医疗课程";
            case TrainingVideoRspDTO.CATEGORY_SCIENCE:
                return "科普课程";
            default:
                return "未知类型";
        }
    }
}
