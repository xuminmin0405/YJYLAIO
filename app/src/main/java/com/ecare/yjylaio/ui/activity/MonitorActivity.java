package com.ecare.yjylaio.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.SimpleActivity;
import com.ecare.yjylaio.config.Constants;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: MonitorActivity
 * Description:
 * Author:
 * CreateDate: 2022/3/21 16:32
 * Version: 1.0
 */
public class MonitorActivity extends SimpleActivity {

    @BindView(R.id.tv_passenger_flow)
    TextView tvPassengerFlow;

    @Override
    protected int getLayoutId() {
        return R.layout.act_monitor;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        tvPassengerFlow.setText(getIntent().getIntExtra(Constants.IT_NAME, 0) + "");
    }

    @OnClick({R.id.ll_one, R.id.ll_two, R.id.ll_three, R.id.ll_four, R.id.ll_five})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_one:
                intent = new Intent(mContext, VideoPlayerActivity.class);
                intent.putExtra(Constants.IT_TITLE, "展厅");
                intent.putExtra(Constants.IT_URL, "https://cmgw-vpc.lechange.com:8890/LCO/5M06E39PAJ27EA1/0/1/20201109T053114/dev_5M06E39PAJ27EA1_20201109T053114.m3u8");
                startActivity(intent);
                break;
            case R.id.ll_two:
                intent = new Intent(mContext, VideoPlayerActivity.class);
                intent.putExtra(Constants.IT_TITLE, "走廊A");
                intent.putExtra(Constants.IT_URL, "https://cmgw-vpc.lechange.com:8890/LCO/6F0B280PAZD68C7/0/1/20220318T073623/a818ad511ce27e6efb4aefce9eacbb60.m3u8");
                startActivity(intent);
                break;
            case R.id.ll_three:
                intent = new Intent(mContext, VideoPlayerActivity.class);
                intent.putExtra(Constants.IT_TITLE, "走廊B");
                intent.putExtra(Constants.IT_URL, "https://cmgw-vpc.lechange.com:8890/LCO/6F0B285PAZ61CEF/0/1/20220318T073807/c3b444c1366c394aecd2386b8f8ac072.m3u8");
                startActivity(intent);
                break;
            case R.id.ll_four:
                intent = new Intent(mContext, VideoPlayerActivity.class);
                intent.putExtra(Constants.IT_TITLE, "多功能厅");
                intent.putExtra(Constants.IT_URL, "https://cmgw-vpc.lechange.com:8890/LCO/6F0B285PAZ80CE3/0/1/20220318T070502/8ee197112c6e99ea52b9a451d5e32390.m3u8");
                startActivity(intent);
                break;
            case R.id.ll_five:
                intent = new Intent(mContext, VideoPlayerActivity.class);
                intent.putExtra(Constants.IT_TITLE, "活动室");
                intent.putExtra(Constants.IT_URL, "https://cmgw-vpc.lechange.com:8890/LCO/7G036A4PCG527C1/0/1/20220323T034018/f0462f529c55f051a3f608b38d71ef13.m3u8");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doBusiness() {

    }
}
