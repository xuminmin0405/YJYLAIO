package com.ecare.yjylaio.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.BaseFragmentPageAdapter;
import com.ecare.yjylaio.base.SimpleActivity;
import com.ecare.yjylaio.model.bean.rsp.TrainingVideoRspDTO;
import com.ecare.yjylaio.ui.fragment.CheatedListFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: CheatedActivity
 * Description:
 * Author:
 * CreateDate: 2021/6/17 18:54
 * Version: 1.0
 */
public class CheatedActivity extends SimpleActivity {

    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //TabLayout
    @BindView(R.id.tl_cheated)
    TabLayout tlCheated;
    //ViewPager
    @BindView(R.id.vp_cheated)
    ViewPager vp_cheated;

    @Override
    protected int getLayoutId() {
        return R.layout.act_cheated;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        //设置标题
        tvTitle.setText("不上当");
        //设置ViewPager
        String[] titles = {"全部", "投资理财", "保健品", "其他"};
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CheatedListFragment.newInstance(TrainingVideoRspDTO.CATEGORY_ALL));
        fragmentList.add(CheatedListFragment.newInstance(TrainingVideoRspDTO.CATEGORY_INVESTMENT));
        fragmentList.add(CheatedListFragment.newInstance(TrainingVideoRspDTO.CATEGORY_HEALTH_PRODUCTS));
        fragmentList.add(CheatedListFragment.newInstance(TrainingVideoRspDTO.CATEGORY_OTHER));
        vp_cheated.setAdapter(new BaseFragmentPageAdapter(getSupportFragmentManager(), fragmentList, titles));
        tlCheated.setupWithViewPager(vp_cheated);
    }

    /**
     * 点击事件
     *
     * @param view view
     */
    @OnClick({R.id.iv_back, R.id.ll_police, R.id.ll_lawyer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_police:
                PhoneUtils.dial("18868795382");
                break;
            case R.id.ll_lawyer:
                PhoneUtils.dial("18657575807");
                break;
            default:
                break;
        }
    }

    @Override
    protected void doBusiness() {

    }
}
