package com.ecare.yjylaio.lechange.openapi;

import android.os.Handler;
import android.os.Message;

import com.ecare.yjylaio.lechange.openapi.entity.DeviceDetailListData;
import com.ecare.yjylaio.lechange.openapi.entity.SubAccountDeviceData;
import com.mm.android.deviceaddmodule.mobilecommon.AppConsume.BusinessException;
import com.mm.android.deviceaddmodule.mobilecommon.AppConsume.BusinessRunnable;
import com.mm.android.deviceaddmodule.mobilecommon.base.LCBusinessHandler;
import com.mm.android.deviceaddmodule.mobilecommon.businesstip.BusinessErrorTip;
import com.mm.android.deviceaddmodule.mobilecommon.businesstip.HandleMessageCode;

public class DeviceSubAccountListService {
    public static final int pageSize=10;

    public void getDeviceListSubAccount(final int pageNo, final String openid, final IGetDeviceInfoCallBack.ISubAccountDevice<SubAccountDeviceData.Response> getDeviceListCallBack){
        final LCBusinessHandler handler = new LCBusinessHandler() {
            @Override
            public void handleBusiness(Message msg) {
                if (getDeviceListCallBack == null) {
                    return;
                }
                if (msg.what == HandleMessageCode.HMC_SUCCESS) {
                    //成功
                    getDeviceListCallBack.DeviceList((SubAccountDeviceData.Response) msg.obj);
                } else {
                    //失败
                    getDeviceListCallBack.onError(BusinessErrorTip.throwError(msg));
                }
            }
        };
        new BusinessRunnable(handler) {
            @Override
            public void doBusiness() throws BusinessException {
                try {
                    dealSubAccountDeviceList(handler,pageNo,openid);
                } catch (BusinessException e) {
                    throw e;
                }
            }
        };
    }

    private void dealSubAccountDeviceList(Handler handler,int pageNo,String openid)throws BusinessException{
        SubAccountDeviceData.Response response= DeviceInfoOpenApiManager.getSubAccountDeviceListLight(pageNo,pageSize,openid);
        handler.obtainMessage(HandleMessageCode.HMC_SUCCESS, response).sendToTarget();
    }

    public void getSubAccountDeviceList(final int pageNo, final IGetDeviceInfoCallBack.ISubAccountDevice<DeviceDetailListData.Response> getDeviceListCallBack){
        final LCBusinessHandler handler = new LCBusinessHandler() {
            @Override
            public void handleBusiness(Message msg) {
                if (getDeviceListCallBack == null) {
                    return;
                }
                if (msg.what == HandleMessageCode.HMC_SUCCESS) {
                    //成功
                    getDeviceListCallBack.DeviceList(( DeviceDetailListData.Response) msg.obj);
                } else {
                    //失败
                    getDeviceListCallBack.onError(BusinessErrorTip.throwError(msg));
                }
            }
        };
        new BusinessRunnable(handler) {
            @Override
            public void doBusiness() throws BusinessException {
                try {
                    getDeviceListAtSubAccount(handler,pageNo);
                } catch (BusinessException e) {
                    throw e;
                }
            }
        };
    }

    private void getDeviceListAtSubAccount(Handler handler,int pageNo)throws BusinessException{
        DeviceDetailListData.Response response= DeviceInfoOpenApiManager.getSubAccountDeviceList(pageNo,pageSize);
        handler.obtainMessage(HandleMessageCode.HMC_SUCCESS, response).sendToTarget();
    }

}
