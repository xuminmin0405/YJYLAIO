package com.ecare.yjylaio.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.SimpleActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.model.bean.rsp.TrainingVideoRspDTO;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: EntertainmentActivity
 * Description:
 * Author:
 * CreateDate: 2021/6/19 14:25
 * Version: 1.0
 */
public class EntertainmentActivity extends SimpleActivity {

    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.act_entertainment;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        //设置标题
        tvTitle.setText("健康宣教");
    }

    /**
     * 点击事件
     *
     * @param view view
     */
    @OnClick({R.id.iv_back, R.id.ll_fitness, R.id.ll_education, R.id.ll_medical, R.id.ll_science})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_fitness:
                startEntertainmentListActivity(TrainingVideoRspDTO.CATEGORY_FITNESS, "科学健身");
                break;
            case R.id.ll_education:
                startEntertainmentListActivity(TrainingVideoRspDTO.CATEGORY_EDUCATION, "教育课程");
                break;
            case R.id.ll_medical:
                startEntertainmentListActivity(TrainingVideoRspDTO.CATEGORY_MEDICAL, "医疗课程");
                break;
            case R.id.ll_science:
                startEntertainmentListActivity(TrainingVideoRspDTO.CATEGORY_SCIENCE, "科普课程");
                break;
            default:
                break;
        }
    }

    /**
     * 开启EntertainmentListActivity
     *
     * @param category 分类
     */
    private void startEntertainmentListActivity(String category, String title) {
        Intent intent = new Intent(mContext, EntertainmentListActivity.class);
        intent.putExtra(Constants.IT_CATEGORY, category);
        intent.putExtra(Constants.IT_TITLE, title);
        startActivity(intent);
    }

    @Override
    protected void doBusiness() {

    }
}
