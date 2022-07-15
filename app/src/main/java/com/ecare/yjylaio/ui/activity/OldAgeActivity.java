package com.ecare.yjylaio.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.SimpleActivity;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: OldAgeActivity
 * Description:
 * Author:
 * CreateDate: 2022/3/21 15:04
 * Version: 1.0
 */
public class OldAgeActivity extends SimpleActivity {

    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.act_old_age;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        //设置标题
        tvTitle.setText("老有所为");
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

    }
}
