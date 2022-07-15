package com.ecare.yjylaio.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.BaseActivity;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.contract.SelfAssessmentNewContract;
import com.ecare.yjylaio.model.bean.rsp.SelfAssessmentRspDTO;
import com.ecare.yjylaio.presenter.SelfAssessmentNewPresenter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: SelfAssessmentNewActivity
 * Description:
 * Author:
 * CreateDate: 2022/4/6 13:53
 * Version: 1.0
 */
public class SelfAssessmentNewActivity extends BaseActivity<SelfAssessmentNewContract.Presenter> implements SelfAssessmentNewContract.View {

    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //内容
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id_card)
    TextView tvIDCard;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_assess_result)
    TextView tvAssessResult;
    @BindView(R.id.tv_org_name)
    TextView tvOrgName;
    @BindView(R.id.tv_assess_per)
    TextView tvAssessPer;
    @BindView(R.id.tv_assess_time)
    TextView tvAssessTime;
    //空布局
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    //LoadingPopupView
    private LoadingPopupView mPop;
    //Disposable
    private Disposable mSubscribe;

    @Override
    protected SelfAssessmentNewContract.Presenter createPresenter() {
        return new SelfAssessmentNewPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_self_assessment_new;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        //设置标题
        tvTitle.setText("评估结果");
        //设置空界面
        ((TextView) llEmpty.getChildAt(1)).setText("未查询到老人信息！");
        //默认都隐藏
        llContent.setVisibility(View.GONE);
        llEmpty.setVisibility(View.GONE);
    }

    @OnClick({R.id.iv_back,R.id.tv_print})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_print:
                showMsg("正在打印中");
                break;
            default:
                break;
        }
    }

    @Override
    protected void doBusiness() {
        showLoadingDialog();
        mSubscribe = Flowable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mPresenter.getSelfAssessment(getIntent().getStringExtra(Constants.IT_ID_CARD));
                    }
                });
    }

    @Override
    public void setSelfAssessment(SelfAssessmentRspDTO data) {
        if (data == null) {
            llContent.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
        } else {
            llContent.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
            tvName.setText(data.getName());
            tvIDCard.setText(desensitizedIdNumber(data.getIdCard()));
            tvAge.setText(data.getAge() + "岁");
            tvMobile.setText(desensitizedPhoneNumber(data.getMobile()));
            tvAddress.setText(data.getAddress());
            String result = data.getResult();
            String resultStr;
            if (StringUtils.isEmpty(result)) {
                resultStr = "未知结果";
            } else {
                switch (result) {
                    case "01":
                        resultStr = "能力完好";
                        break;
                    case "02":
                        resultStr = "轻度失能";
                        break;
                    case "03":
                        resultStr = "中度失能";
                        break;
                    case "04":
                        resultStr = "重度失能";
                        break;
                    default:
                        resultStr = "未知结果";
                        break;
                }
            }
            tvAssessResult.setText(resultStr);
            tvOrgName.setText(data.getOrgName());
            tvAssessPer.setText(data.getAssessPer());
            tvAssessTime.setText(data.getAssessTime());
        }
    }

    @Override
    public void showLoadingDialog() {
        if (mPop == null) {
            mPop = new XPopup.Builder(mContext)
                    .asLoading("能力评估组件调用成功");
        }
        mPop.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (mPop == null) {
            return;
        }
        mPop.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscribe.dispose();
    }

    private static String desensitizedPhoneNumber(String phoneNumber) {
        if (!StringUtils.isEmpty(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
        }
        return phoneNumber;
    }

    private static String desensitizedIdNumber(String idNumber) {
        if (!StringUtils.isEmpty(idNumber)) {
            if (idNumber.length() == 15) {
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
            }
            if (idNumber.length() == 18) {
                idNumber = idNumber.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
            }
        }
        return idNumber;
    }
}
