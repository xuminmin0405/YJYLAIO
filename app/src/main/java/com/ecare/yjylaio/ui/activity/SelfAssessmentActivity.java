package com.ecare.yjylaio.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.BaseActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.contract.SelfAssessmentContract;
import com.ecare.yjylaio.model.bean.rsp.SelfAssessmentQuestionRspDTO;
import com.ecare.yjylaio.presenter.SelfAssessmentPresenter;
import com.ecare.yjylaio.widght.HorizontalDividerItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * NeighborhoodLongevity
 * <p>
 * Created by xuminmin on 12/10/21.
 * Email: iminminxu@gmail.com
 */
public class SelfAssessmentActivity extends BaseActivity<SelfAssessmentContract.Presenter> implements SelfAssessmentContract.View {

    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //加载组件
    @BindView(R.id.view_main)
    SmartRefreshLayout srlRefresh;
    //题目列表
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    //题目列表适配器
    private BaseQuickAdapter<SelfAssessmentQuestionRspDTO, BaseViewHolder> mAdapter;

    @Override
    protected SelfAssessmentContract.Presenter createPresenter() {
        return new SelfAssessmentPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_entertainment_list;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        //设置标题
        tvTitle.setText("能力自评");
        //初始化SmartRefreshLayout
        initSmartRefreshLayout();
        //初始化题目列表
        initRecyclerView();
    }

    /**
     * 初始化SmartRefreshLayout
     */
    private void initSmartRefreshLayout() {
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setEnableLoadMore(false);
    }

    /**
     * 初始化题目列表
     */
    private void initRecyclerView() {
        //初始化题目数据
        List<SelfAssessmentQuestionRspDTO> data = new ArrayList<>();
        List<SelfAssessmentQuestionRspDTO.OptionDTO> optionOne = new ArrayList<>();
        optionOne.add(new SelfAssessmentQuestionRspDTO.OptionDTO("A", "使用餐具将饭菜送入口中、咀嚼、吞咽等步骤"));
        optionOne.add(new SelfAssessmentQuestionRspDTO.OptionDTO("B", "使用餐具，在切碎、搅拌等协助下能完成"));
        optionOne.add(new SelfAssessmentQuestionRspDTO.OptionDTO("C", "使用餐具有困难，进食需要帮助"));
        optionOne.add(new SelfAssessmentQuestionRspDTO.OptionDTO("D", "不能自主进食，或伴有吞咽困难，完全需要帮助(如喂食、鼻饲等)"));
        data.add(new SelfAssessmentQuestionRspDTO("1.进食", optionOne));
        List<SelfAssessmentQuestionRspDTO.OptionDTO> optionTwo = new ArrayList<>();
        optionTwo.add(new SelfAssessmentQuestionRspDTO.OptionDTO("A", "独立完成"));
        optionTwo.add(new SelfAssessmentQuestionRspDTO.OptionDTO("B", "头部清洁能独立完成，洗浴需要协助"));
        optionTwo.add(new SelfAssessmentQuestionRspDTO.OptionDTO("C", "在他人协助下能完成部分头部清洁；洗浴需要帮助"));
        optionTwo.add(new SelfAssessmentQuestionRspDTO.OptionDTO("D", "完全需要帮助"));
        data.add(new SelfAssessmentQuestionRspDTO("2.头部清洁及洗浴", optionTwo));
        List<SelfAssessmentQuestionRspDTO.OptionDTO> optionThree = new ArrayList<>();
        optionThree.add(new SelfAssessmentQuestionRspDTO.OptionDTO("A", "独立完成"));
        optionThree.add(new SelfAssessmentQuestionRspDTO.OptionDTO("B", "需要他人协助，在适当时间内完成部分穿衣"));
        optionThree.add(new SelfAssessmentQuestionRspDTO.OptionDTO("C", "在他人协助下，仍需在较长时间内完成部分穿衣"));
        optionThree.add(new SelfAssessmentQuestionRspDTO.OptionDTO("D", "完全需要帮助"));
        data.add(new SelfAssessmentQuestionRspDTO("3.穿衣", optionThree));
        List<SelfAssessmentQuestionRspDTO.OptionDTO> optionFour = new ArrayList<>();
        optionFour.add(new SelfAssessmentQuestionRspDTO.OptionDTO("A", "如厕不需协助"));
        optionFour.add(new SelfAssessmentQuestionRspDTO.OptionDTO("B", "在适当提示和协助下能如厕或使用便盆"));
        optionFour.add(new SelfAssessmentQuestionRspDTO.OptionDTO("C", "在很多提示下能协助下尚能如厕或使用便盆"));
        optionFour.add(new SelfAssessmentQuestionRspDTO.OptionDTO("D", "如厕完全需要帮助"));
        data.add(new SelfAssessmentQuestionRspDTO("4.大小便如厕", optionFour));
        List<SelfAssessmentQuestionRspDTO.OptionDTO> optionFive = new ArrayList<>();
        optionFive.add(new SelfAssessmentQuestionRspDTO.OptionDTO("A", "独立完成"));
        optionFive.add(new SelfAssessmentQuestionRspDTO.OptionDTO("B", "借助较小外力或辅助装置能完成站立、转移、行走、上下楼等"));
        optionFive.add(new SelfAssessmentQuestionRspDTO.OptionDTO("C", "动则气急喘息，借助较大外力才能完成站立、转移、行走、不能上下楼"));
        optionFive.add(new SelfAssessmentQuestionRspDTO.OptionDTO("D", "卧床不起；休息状态下时有气息喘息，难以站立；移动完全需要帮助"));
        data.add(new SelfAssessmentQuestionRspDTO("5.移动", optionFive));
        List<SelfAssessmentQuestionRspDTO.OptionDTO> optionSix = new ArrayList<>();
        optionSix.add(new SelfAssessmentQuestionRspDTO.OptionDTO("A", "无帕金森病、中风后遗症等导致上述能力受限的疾病"));
        optionSix.add(new SelfAssessmentQuestionRspDTO.OptionDTO("B", "有帕金森病、中风后遗症等导致上述能力受限的疾病"));
        optionSix.add(new SelfAssessmentQuestionRspDTO.OptionDTO("C", "有帕金森病、中风后遗症等导致上述能力受限的疾病一种以上"));
        data.add(new SelfAssessmentQuestionRspDTO("6.疾病", optionSix));
        //初始化题目列表
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new BaseQuickAdapter<SelfAssessmentQuestionRspDTO, BaseViewHolder>(R.layout.item_self_assessment_question, data) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, SelfAssessmentQuestionRspDTO item) {
                if (item == null) {
                    return;
                }
                helper.setText(R.id.tv_title, item.getTitle());
                RecyclerView rvOption = helper.getView(R.id.rv_option);
                rvOption.setLayoutManager(new LinearLayoutManager(mContext));
                BaseQuickAdapter<SelfAssessmentQuestionRspDTO.OptionDTO, BaseViewHolder> optionAdapter = new BaseQuickAdapter<SelfAssessmentQuestionRspDTO.OptionDTO, BaseViewHolder>(R.layout.item_self_assessment_option, item.getOption()) {
                    @Override
                    protected void convert(@NotNull BaseViewHolder holder, SelfAssessmentQuestionRspDTO.OptionDTO optionDTO) {
                        if (optionDTO == null) {
                            return;
                        }
                        holder.setText(R.id.tv_option, optionDTO.getOption())
                                .setTextColor(R.id.tv_option, getResources().getColor(holder.getLayoutPosition() + 1 == item.getAnswer() ? R.color.colorWhite : R.color.color999999))
                                .setBackgroundResource(R.id.tv_option, holder.getLayoutPosition() + 1 == item.getAnswer() ? R.drawable.oval_0082ff : R.drawable.oval_stroke_2_aeaeae)
                                .setText(R.id.tv_option_content, optionDTO.getOptionContent());
                    }
                };
                optionAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                        item.setAnswer(position + 1);
                        optionAdapter.notifyDataSetChanged();
                    }
                });
                rvOption.setAdapter(optionAdapter);
            }
        };
        rvList.setAdapter(mAdapter);
        TextView tvSubmit = (TextView) LayoutInflater.from(mContext).inflate(R.layout.foot_self_assessment, rvList, false);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfAssessmentSubmit();
            }
        });
        mAdapter.setFooterView(tvSubmit);
        rvList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .color(Color.TRANSPARENT)
                .sizeResId(R.dimen.dp_15)
                .showLastDivider()
                .build());
    }

    /**
     * 提交能力自评
     */
    private void selfAssessmentSubmit() {
        for (int i = 0; i < mAdapter.getData().size(); i++) {
            SelfAssessmentQuestionRspDTO item = mAdapter.getItem(i);
            if (item == null) {
                continue;
            }
            if (item.getAnswer() == 0) {
                showMsg("请选择第" + (i + 1) + "题");
                return;
            }
        }
        mPresenter.selfAssessmentSubmit(getIntent().getStringExtra(Constants.IT_ID_CARD), mAdapter.getItem(0).getAnswer(), mAdapter.getItem(1).getAnswer(), mAdapter.getItem(2).getAnswer(), mAdapter.getItem(3).getAnswer(), mAdapter.getItem(4).getAnswer(), mAdapter.getItem(5).getAnswer());
    }

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

    }

    /**
     * 提交能力自评成功
     */
    @Override
    public void selfAssessmentSubmitSuc() {
        showMsg("提交成功");
        finish();
    }
}
