package com.mm.android.deviceaddmodule.presenter;

import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.dahua.mobile.utility.network.DHWifiUtil;
import com.mm.android.deviceaddmodule.R;
import com.mm.android.deviceaddmodule.contract.DevSecCodeConstract;
import com.mm.android.deviceaddmodule.helper.DeviceAddHelper;
import com.mm.android.deviceaddmodule.mobilecommon.AppConsume.BusinessException;
import com.mm.android.deviceaddmodule.mobilecommon.base.LCBusinessHandler;
import com.mm.android.deviceaddmodule.mobilecommon.businesstip.BusinessErrorTip;
import com.mm.android.deviceaddmodule.mobilecommon.businesstip.HandleMessageCode;
import com.mm.android.deviceaddmodule.mobilecommon.entity.deviceadd.DeviceAddInfo;
import com.mm.android.deviceaddmodule.mobilecommon.utils.StringUtils;
import com.mm.android.deviceaddmodule.model.DeviceAddModel;

import java.lang.ref.WeakReference;

public class DevSecCodePresenter implements DevSecCodeConstract.Presenter {
    WeakReference<DevSecCodeConstract.View> mView;
    String mDeviceSn;
    boolean mIsWifiOfflineMode;
    DHWifiUtil mDHWifiUtil;

    public DevSecCodePresenter(DevSecCodeConstract.View view) {
        mView = new WeakReference<>(view);
        mDeviceSn = DeviceAddModel.newInstance().getDeviceInfoCache().getDeviceSn();
        mIsWifiOfflineMode = DeviceAddModel.newInstance().getDeviceInfoCache().isWifiOfflineMode();
        mDHWifiUtil = new DHWifiUtil(mView.get().getContextInfo());
    }

    @Override
    public void validate() {
        if (!checkInput(mView.get().getDeviceSecCode())) {
            mView.get().showToastInfo(R.string.mobile_common_bec_add_device_valid_error);
            return;
        }
        mView.get().showProgressDialog();
        bindDevice(mView.get().getDeviceSecCode());
    }

    private boolean checkInput(String regCode) {
        return !(TextUtils.isEmpty(regCode) || regCode.length() < 6);
    }

    private void bindDevice(final String code) {
        final DeviceAddInfo deviceAddInfo = DeviceAddModel.newInstance().getDeviceInfoCache();
        String sn = deviceAddInfo.getDeviceSn();
        String pwd = deviceAddInfo.getDevicePwd();
        // ???auth????????????????????????????????????(????????????????????????auth?????????)
        String devPwd = TextUtils.isEmpty(pwd) ? "" : StringUtils.getRTSPAuthPassword(pwd, sn);
        String userName = StringUtils.getRTSPAuthPassword("admin", sn);
        DeviceAddInfo.GPSInfo gpsInfo = deviceAddInfo.getGpsInfo();
        LCBusinessHandler bindHandler = new LCBusinessHandler() {
            @Override
            public void handleBusiness(Message msg) {
                if (mView.get() == null
                        || (mView.get() != null && !mView.get().isViewActive())) {
                    return;
                }
                mView.get().cancelProgressDialog();
                if (msg.what == HandleMessageCode.HMC_SUCCESS) {
                    if (DeviceAddInfo.BindStatus.bindByOther.name().equals(deviceAddInfo.getBindStatus())) {           //???????????????????????????
                        mView.get().goOtherUserBindTipPage();
                        return;
                    }
                    addDeviceToPolicy(code);
                   /* if (DeviceAddInfo.BindStatus.bindByMe.name().equals(deviceAddInfo.getBindStatus())) {                     //???????????????????????????
                        mView.get().showToastInfo(R.string.add_device_device_bind_by_yourself);
                        mView.get().completeAction();
                    } else if (DeviceAddInfo.BindStatus.bindByOther.name().equals(deviceAddInfo.getBindStatus())) {           //???????????????????????????
                        mView.get().goOtherUserBindTipPage();
                    } else {
                        mView.get().goBindSuceesPage();
                    }*/
                } else {
                    String errorDesp = ((BusinessException) msg.obj).errorDescription;
                    if (errorDesp.contains("DV1014")) {  //??????????????????????????????10??????????????????30??????
                        mView.get().goErrorTipPage(DeviceAddHelper.ErrorCode.COMMON_ERROR_DEVICE_BIND_MROE_THAN_TEN);
                    } else if (errorDesp.contains("DV1015")) {  //??????????????????????????????20??????????????????24??????
                        mView.get().goErrorTipPage(DeviceAddHelper.ErrorCode.COMMON_ERROR_DEVICE_MROE_THAN_TEN_TWICE);
                    } else if (errorDesp.contains("DV1045")) {  // ????????????
                        mView.get().goErrorTipPage(DeviceAddHelper.ErrorCode.COMMON_ERROR_DEVICE_SN_CODE_CONFLICT);
                    } else if (errorDesp.contains("DV1044")) {  //??????IP????????????????????????
                        mView.get().goErrorTipPage(DeviceAddHelper.ErrorCode.COMMON_ERROR_DEVICE_IP_ERROR);
                    } else if (errorDesp.contains("DV1027")) { // ?????????????????????
                        mView.get().showToastInfo(BusinessErrorTip.getErrorTip(msg));
                    } else if (errorDesp.contains("DV1016")||errorDesp.contains("DV1025")) {  // ??????????????????(?????????????????????/??????SC????????????????????????
                        mView.get().showToastInfo(R.string.add_device_verify_device_pwd_failed);
                        mView.get().goDevLoginPage();
                        deviceAddInfo.setDevicePwd("");
                    } else if (errorDesp.contains("DV1037")) {  //NB?????????imei???device id ?????????
                        mView.get().goErrorTipPage(DeviceAddHelper.ErrorCode.COMMON_ERROR_DEVICE_SN_OR_IMEI_NOT_MATCH);
                    } else {
                        mView.get().showToastInfo(BusinessErrorTip.getErrorTip(msg));
                    }
                }
            }
        };
        DeviceAddModel.newInstance().bindDevice(sn, code, "", "", gpsInfo.getLongitude(), gpsInfo.getLatitude(), userName, code, bindHandler);
    }

    private void addDeviceToPolicy(String sn){
        LCBusinessHandler policyHandler = new LCBusinessHandler(Looper.getMainLooper()) {

            @Override
            public void handleBusiness(Message msg) {
                if (msg.what == HandleMessageCode.HMC_SUCCESS){
                    mView.get().goBindSuceesPage();
                }else{
                    mView.get().showToastInfo(BusinessErrorTip.getErrorTip(msg));
                }
            }
        };
        DeviceAddModel.newInstance().addPolicy(sn,policyHandler);
    }
}
