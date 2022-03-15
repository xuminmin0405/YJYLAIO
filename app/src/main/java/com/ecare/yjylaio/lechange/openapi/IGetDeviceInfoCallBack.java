package com.ecare.yjylaio.lechange.openapi;

import com.ecare.yjylaio.lechange.openapi.entity.CloudRecordsData;
import com.ecare.yjylaio.lechange.openapi.entity.DeviceChannelInfoData;
import com.ecare.yjylaio.lechange.openapi.entity.DeviceDetailListData;
import com.ecare.yjylaio.lechange.openapi.entity.DeviceLocalCacheData;
import com.ecare.yjylaio.lechange.openapi.entity.DeviceVersionListData;
import com.ecare.yjylaio.lechange.openapi.entity.LocalRecordsData;
import com.mm.android.deviceaddmodule.device_wifi.CurWifiInfo;

public interface IGetDeviceInfoCallBack {
    public interface IDeviceListCallBack {
        /**
         * 成功获取到设备列表
         *
         * @param responseData
         */
        void deviceList(DeviceDetailListData.Response responseData);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IDeviceVersionCallBack {
        /**
         * 获取设备版本和可升级信息
         *
         * @param responseData
         */
        void deviceVersion(DeviceVersionListData.Response responseData);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IModifyDeviceCallBack {
        /**
         * 修改设备或通道名称成功
         *
         * @param result
         */
        void deviceModify(boolean result);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IUnbindDeviceCallBack {
        /**
         * 解绑设备成功
         *
         * @param result
         */
        void unBindDevice(boolean result);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IDeviceChannelInfoCallBack {
        /**
         * 单个设备通道的详细信息获取
         *
         * @param result
         */
        void deviceChannelInfo(DeviceChannelInfoData.Response result);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IDeviceAlarmStatusCallBack {
        /**
         * 设置动检开关
         *
         * @param result
         */
        void deviceAlarmStatus(boolean result);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IDeviceUpdateCallBack {
        /**
         * 设备升级
         *
         * @param result
         */
        void deviceUpdate(boolean result);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IDeviceCloudRecordCallBack {
        /**
         * 倒序查询设备云录像片段
         *
         * @param result
         */
        void deviceCloudRecord(CloudRecordsData.Response result);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IDeviceLocalRecordCallBack {
        /**
         * 查询设备设备录像片段
         *
         * @param result
         */
        void deviceLocalRecord(LocalRecordsData.Response result);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IDeviceDeleteRecordCallBack {
        /**
         * 删除设备云录像片段
         */
        void deleteDeviceRecord();

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IDeviceCacheCallBack {
        /**
         * 获取设备缓存信息
         */
        void deviceCache(DeviceLocalCacheData deviceLocalCacheData);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IDeviceCurrentWifiInfoCallBack {
        /**
         * 设备当前连接热点信息
         */
        void deviceCurrentWifiInfo(CurWifiInfo curWifiInfo);

        /**
         * 错误回调
         *
         * @param throwable
         */
        void onError(Throwable throwable);
    }

    public interface IModifyDeviceName {
        /**
         * 设备修改之后的名字
         */
        void deviceName(String newName);
    }

    public interface ISubAccountDevice<T> {
        void DeviceList(T response);

        void onError(Throwable throwable);
    }

    public interface ICommon<T> {
        void onCommonBack(T response);

        void onError(Throwable throwable);
    }


}
