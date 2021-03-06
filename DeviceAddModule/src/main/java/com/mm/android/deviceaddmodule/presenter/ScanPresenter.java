package com.mm.android.deviceaddmodule.presenter;

import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.dahua.mobile.utility.music.DHMusicPlayer;
import com.mm.android.deviceaddmodule.R;
import com.mm.android.deviceaddmodule.contract.ScanContract;
import com.mm.android.deviceaddmodule.event.DeviceAddEvent;
import com.mm.android.deviceaddmodule.helper.DeviceAddHelper;
import com.mm.android.deviceaddmodule.mobilecommon.AppConsume.BusinessException;
import com.mm.android.deviceaddmodule.mobilecommon.AppConsume.ProviderManager;
import com.mm.android.deviceaddmodule.mobilecommon.AppConsume.ScanResult;
import com.mm.android.deviceaddmodule.mobilecommon.AppConsume.ScanResultFactory;
import com.mm.android.deviceaddmodule.mobilecommon.annotation.DeviceAbility;
import com.mm.android.deviceaddmodule.mobilecommon.base.LCBusinessHandler;
import com.mm.android.deviceaddmodule.mobilecommon.businesstip.BusinessErrorTip;
import com.mm.android.deviceaddmodule.mobilecommon.businesstip.HandleMessageCode;
import com.mm.android.deviceaddmodule.mobilecommon.common.LCConfiguration;
import com.mm.android.deviceaddmodule.mobilecommon.entity.deviceadd.DeviceAddInfo;
import com.mm.android.deviceaddmodule.mobilecommon.entity.deviceadd.DeviceIntroductionInfo;
import com.mm.android.deviceaddmodule.mobilecommon.utils.LogUtil;
import com.mm.android.deviceaddmodule.model.DeviceAddModel;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;

import static com.mm.android.deviceaddmodule.helper.Utils4AddDevice.isDeviceTypeBox;

public class ScanPresenter implements ScanContract.Presenter {
    WeakReference<ScanContract.View> mView;
    DHMusicPlayer mDHMusicPlayer;

    public ScanPresenter(ScanContract.View view) {
        mView = new WeakReference<>(view);
        mDHMusicPlayer = new DHMusicPlayer(mView.get().getContextInfo(), false, true, R.raw.beep);
        mDHMusicPlayer.setSupportVibrate(true);
    }

    @Override
    public ScanResult parseScanStr(String scanStr, String sc) {
        if (!isManualInputPage()) {
            mDHMusicPlayer.playRing(false);
        }
        ScanResult scanResult = ScanResultFactory.scanResult(scanStr.trim());
        LogUtil.debugLog("ScanPresenter", "scanResult : " + scanResult);
        // ??????????????????????????????
        if (!TextUtils.isEmpty(sc)) {
            scanResult.setSc(sc);
        }
        if (!TextUtils.isEmpty(scanResult.getSn())) {
            updateDeviceAddInfo(scanResult.getSn().trim(), scanResult.getMode(), scanResult.getRegcode(), scanResult.getNc(), scanResult.getSc(), scanResult.getImeiCode());
        }
        return scanResult;
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param deviceSn
     * @param deviceCodeModel
     */
    @Override
    public void getDeviceInfo(final String deviceSn, final String deviceCodeModel) {
        if (isSnInValid(deviceSn)) {
            mView.get().showToastInfo(R.string.add_device_scan_lechange_device_qr_code);
        } else {
            mView.get().showProgressDialog();
            LCBusinessHandler getDeviceHandler = new LCBusinessHandler() {
                @Override
                public void handleBusiness(Message msg) {
                    if (mView.get() == null
                            || (mView.get() != null && !mView.get().isViewActive())) {
                        return;
                    }
                    mView.get().cancelProgressDialog();
                    if (msg.what == HandleMessageCode.HMC_SUCCESS) {

                    } else {
                        String errorDesp = ((BusinessException) msg.obj).errorDescription;
                        if (errorDesp.contains("DV1037")) {
                            mView.get().showToastInfo(R.string.add_device_device_sn_or_imei_not_match);
                            return;
                        }
                        if (errorDesp.contains("DV1003")){
                            addDeviceToPolicy(deviceSn);
                            return;
                        }
                        mView.get().showToastInfo(BusinessErrorTip.getErrorTip(msg));
                        return;
                    }
                    dispatchResult();
                }
            };
            DeviceAddModel.newInstance().getDeviceInfo(deviceSn, deviceCodeModel, "", "", getDeviceHandler);
        }
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

    private void getDevIntroductionInfoSync(String deviceModel, final boolean isOnlineAction) {
        LCBusinessHandler getDevIntroductionHandler = new LCBusinessHandler() {
            @Override
            public void handleBusiness(Message msg) {
                if (mView.get() == null
                        || (mView.get() != null && !mView.get().isViewActive())) {
                    return;
                }
                dispatchIntroductionResult(isOnlineAction);
            }
        };
        DeviceAddModel.newInstance().getDevIntroductionInfo(deviceModel, getDevIntroductionHandler);
    }

    private void checkDevIntroductionInfo(final String deviceModelName, final boolean isOnlineAction) {
        mView.get().showProgressDialog();
        LCBusinessHandler checkDevIntroductionHandler = new LCBusinessHandler() {
            @Override
            public void handleBusiness(Message msg) {
                if (mView.get() == null
                        || (mView.get() != null && !mView.get().isViewActive())) {
                    return;
                }
                DeviceIntroductionInfo deviceIntroductionInfo = null;
                if (msg.what == HandleMessageCode.HMC_SUCCESS) {
                    deviceIntroductionInfo = (DeviceIntroductionInfo) msg.obj;
                }
                if (deviceIntroductionInfo == null) {        //??????????????????
                    getDevIntroductionInfoSync(deviceModelName, isOnlineAction);
                } else {
                    dispatchIntroductionResult(isOnlineAction);
                }
            }
        };
        DeviceAddModel.newInstance().checkDevIntroductionInfo(deviceModelName, checkDevIntroductionHandler);
    }

    private void dispatchIntroductionResult(boolean isOnlineAction) {
        mView.get().cancelProgressDialog();
        if (isOnlineAction) {
            gotoPage();
        } else {
            EventBus.getDefault().post(new DeviceAddEvent(DeviceAddEvent.CONFIG_PAGE_NAVIGATION_ACTION));
        }
    }

    //?????????????????????????????????
    @Override
    public boolean isSnInValid(String sn) {
        if (ProviderManager.getAppProvider().getAppType() == LCConfiguration.APP_LECHANGE_OVERSEA) {
            return (sn.length() == 0
                    || sn.getBytes().length > 64);
        } else {
            return TextUtils.isEmpty(sn);
        }
    }

    @Override
    public boolean isScCodeInValid(String scCode) {
        return false;
    }

    @Override
    public void recyle() {
        if (mDHMusicPlayer != null) {
            mDHMusicPlayer.release();
        }
    }

    @Override
    public void resetCache() {
        DeviceAddModel.newInstance().getDeviceInfoCache().clearCache();
    }

    @Override
    public boolean isManualInputPage() {
        return false;
    }

    protected void updateDeviceAddInfo(final String deviceSn, final String model, String regCode, String nc, String sc, String imeiCode) {
        DeviceAddInfo deviceAddInfo = DeviceAddModel.newInstance().getDeviceInfoCache();
        deviceAddInfo.setDeviceSn(deviceSn);
        deviceAddInfo.setDeviceCodeModel(model);
        deviceAddInfo.setDeviceModel(model);
        deviceAddInfo.setRegCode(regCode);
        deviceAddInfo.setSc(sc);
        deviceAddInfo.setNc(nc);  // ???16?????????????????????????????????
        // ??????SC?????????????????????SC?????????????????????
        if (DeviceAddHelper.isSupportAddBySc(deviceAddInfo)) {
            deviceAddInfo.setDevicePwd(sc);
        }
        deviceAddInfo.setImeiCode(imeiCode);
    }

    /**
     * ?????????????????????????????????
     */
    private void dispatchResult() {
        DeviceAddInfo deviceAddInfo = DeviceAddModel.newInstance().getDeviceInfoCache();
        if (!deviceAddInfo.isSupport()) {
            mView.get().goNotSupportBindTipPage();
        } else if (DeviceAddInfo.BindStatus.bindByMe.name().equals(deviceAddInfo.getBindStatus())) {                     //???????????????????????????
            mView.get().showToastInfo(R.string.add_device_device_bind_by_yourself);
        } else if (DeviceAddInfo.BindStatus.bindByOther.name().equals(deviceAddInfo.getBindStatus())) {           //???????????????????????????
            mView.get().goOtherUserBindTipPage();
        } else if (DeviceAddInfo.DeviceType.ap.name().equals(deviceAddInfo.getType())) {        //??????
            checkDevIntroductionInfo(deviceAddInfo.getDeviceModel(), false);
        } else {    // ??????
            if (isManualInputPage()  // ??????????????????sc???????????????ios??????
                    && deviceAddInfo.hasAbility(DeviceAbility.SCCode) && (deviceAddInfo.getSc() == null || deviceAddInfo.getSc().length() != 8)) {   // ???????????????sc????????????sc???????????????
                mView.get().showToastInfo(R.string.add_device_input_corrent_sc_tip);
            } else if (!deviceAddInfo.isDeviceInServer()) {                                                            //???????????????????????????
                //???????????????????????????
                deviceOfflineAction();
            } else if (DeviceAddInfo.Status.offline.name().equals(deviceAddInfo.getStatus())) {                        //????????????
                deviceOfflineAction();
            } else if (DeviceAddInfo.Status.online.name().equals(deviceAddInfo.getStatus())
                    || DeviceAddInfo.Status.sleep.name().equals(deviceAddInfo.getStatus())
                    || DeviceAddInfo.Status.upgrading.name().equals(deviceAddInfo.getStatus())) {                         //????????????/??????/?????????
                deviceOnlineAction();
            }
        }

        if (isManualInputPage()) {
            deviceAddInfo.setStartTime(System.currentTimeMillis());
        }
    }

    /**
     * <p>
     * ????????????????????????????????????????????????????????????????????????????????????
     * </p>
     */
    private void deviceOfflineAction() {
        DeviceAddInfo deviceAddInfo = DeviceAddModel.newInstance().getDeviceInfoCache();
        if (isDeviceTypeBox(deviceAddInfo.getDeviceCodeModel())) {// ???????????????????????????????????????????????????
            mView.get().showToastInfo(R.string.add_device_box_is_offline);
        } else {
            if ((!TextUtils.isEmpty(deviceAddInfo.getDeviceCodeModel())
                    || !TextUtils.isEmpty(deviceAddInfo.getDeviceModel()))) { //?????????????????????????????????
                String deviceModel = deviceAddInfo.getDeviceModel();
                if (TextUtils.isEmpty(deviceModel)) {
                    deviceModel = deviceAddInfo.getDeviceCodeModel();
                }
                checkDevIntroductionInfo(deviceModel, false);
            } else {
                mView.get().goTypeChoosePage();                 //????????????
            }
        }
    }

    /**
     * <p>
     * ??????????????????????????????????????????????????????????????????
     * </p>
     */
    private void deviceOnlineAction() {
        DeviceAddInfo deviceAddInfo = DeviceAddModel.newInstance().getDeviceInfoCache();
        if (isDeviceTypeBox(deviceAddInfo.getDeviceCodeModel())) {
            // ??????????????????
            mView.get().showToastInfo(R.string.add_device_not_support_to_bind);
            return;
        } else {// ????????????
            if (!TextUtils.isEmpty(deviceAddInfo.getDeviceCodeModel())
                    || !TextUtils.isEmpty(deviceAddInfo.getDeviceModel())) { //?????????????????????????????????
                String deviceModel = deviceAddInfo.getDeviceModel();
                if (TextUtils.isEmpty(deviceModel)) {
                    deviceModel = deviceAddInfo.getDeviceCodeModel();
                }
                checkDevIntroductionInfo(deviceModel, true);
            } else {
                gotoPage();
            }
        }
    }

    private void gotoPage() {
        DeviceAddInfo deviceAddInfo = DeviceAddModel.newInstance().getDeviceInfoCache();
        deviceAddInfo.setCurDeviceAddType(DeviceAddInfo.DeviceAddType.ONLINE);

        if (deviceAddInfo.getConfigMode().contains(DeviceAddInfo.ConfigMode.NBIOT.name())) {    //NB??????
            deviceAddInfo.setCurDeviceAddType(DeviceAddInfo.DeviceAddType.NBIOT);
            if (TextUtils.isEmpty(deviceAddInfo.getImeiCode())) {
                mView.get().goIMEIInputPage();  //NB??????????????????IMEI???
            } else {
                mView.get().goDeviceBindPage();//????????????
            }
        } else if (DeviceAddHelper.isSupportAddBySc(deviceAddInfo)) {
            mView.get().goCloudConnectPage();
        } else {
            if (ProviderManager.getAppProvider().getAppType() == LCConfiguration.APP_LECHANGE_OVERSEA) { // ??????
                mView.get().goDeviceLoginPage();
            } else {
                if (deviceAddInfo.hasAbility(DeviceAbility.Auth)) {
                    if (TextUtils.isEmpty(deviceAddInfo.getDevicePwd())) {
                        mView.get().goDeviceLoginPage();//??????????????????
                    } else {
                        mView.get().goDeviceBindPage();//????????????
                    }
                } else if (deviceAddInfo.hasAbility(DeviceAbility.RegCode)) {
                    if (TextUtils.isEmpty(deviceAddInfo.getRegCode())) {
                        mView.get().goSecCodePage();//???????????????
                    } else {
                        mView.get().goDeviceBindPage();//????????????
                    }
                }
            }
        }
    }
}
