package com.ecare.yjylaio.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.RootActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.contract.HelpContract;
import com.ecare.yjylaio.model.bean.rsp.LableRspDTO;
import com.ecare.yjylaio.presenter.HelpPresenter;
import com.ecare.yjylaio.widght.GridSpacingItemDecoration;
import com.ecare.yjylaio.widght.HorizontalDividerItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: YJYLAged
 * Package: com.ecare.yjylaged.ui.activity
 * ClassName: HelpActivity
 * Description:
 * Author:
 * CreateDate: 2021/7/30 15:05
 * Version: 1.0
 */
public class HelpActivity extends RootActivity<HelpContract.Presenter> implements HelpContract.View {

    //根布局
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //加载组件
    @BindView(R.id.view_main)
    SmartRefreshLayout srlRefresh;
    //活动列表
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    //标签适配器
    private BaseQuickAdapter<LableRspDTO, BaseViewHolder> labelAdapter;

    @Override
    protected HelpContract.Presenter createPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_entertainment_list;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        //设置根布局
        llRoot.setBackgroundColor(getResources().getColor(R.color.colorFAFAFA));
        //设置标题
        tvTitle.setText("老有所乐");
        //初始化SmartRefreshLayout
        initSmartRefreshLayout();
        //初始化活动列表
        initRecyclerView();
    }

    /**
     * 初始化SmartRefreshLayout
     */
    private void initSmartRefreshLayout() {
        srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMoreData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refreshData();
                mPresenter.getLabel();
            }
        });
    }

    /**
     * 初始化活动列表
     */
    private void initRecyclerView() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        BaseQuickAdapter adapter = mPresenter.getAdapter();
        rvList.setAdapter(adapter);
        adapter.setEmptyView(R.layout.view_empty);
        rvList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .color(Color.TRANSPARENT)
                .sizeResId(R.dimen.dp_10)
                .showLastDivider()
                .build());
        //设置header
        View header = LayoutInflater.from(mContext).inflate(R.layout.head_help, rvList, false);
        RecyclerView rvLabel = header.findViewById(R.id.rv_label);
        rvLabel.setLayoutManager(new GridLayoutManager(mContext, 3));
        labelAdapter = new BaseQuickAdapter<LableRspDTO, BaseViewHolder>(R.layout.item_help_label, null) {
            @Override
            protected void convert(BaseViewHolder helper, LableRspDTO item) {
                GradientDrawable drawable = new GradientDrawable();
                drawable.setCornerRadius(getResources().getDimension(R.dimen.dp_5));
                drawable.setStroke((int) getResources().getDimension(R.dimen.dp_1), Color.parseColor(item.getTagBorderColor()));
                drawable.setColor(Color.parseColor(item.getTagBorderColor().replace("#", "#33")));
                TextView tvName = helper.getView(R.id.tv_name);
                tvName.setText(item.getLableName());
                tvName.setBackground(drawable);
            }
        };
        labelAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                LableRspDTO item = labelAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                Intent intent = new Intent(mContext, HelpLabelDetailActivity.class);
                intent.putExtra(Constants.IT_ID, item.getId());
                startActivity(intent);
            }
        });
        rvLabel.setAdapter(labelAdapter);
        rvLabel.addItemDecoration(new GridSpacingItemDecoration(3, (int) getResources().getDimension(R.dimen.dp_14), false));
        adapter.addHeaderView(header);
        adapter.setHeaderWithEmptyEnable(true);
    }

    /**
     * 点击事件
     *
     * @param view view
     */
    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void doBusiness() {
        mPresenter.firstLoadData();
        mPresenter.getLabel();
    }

    @Override
    public void setNoMoreData() {
        srlRefresh.setNoMoreData(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter != null) {
            mPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setLabel(List<LableRspDTO> data) {
        labelAdapter.setNewData(data);
    }
}
