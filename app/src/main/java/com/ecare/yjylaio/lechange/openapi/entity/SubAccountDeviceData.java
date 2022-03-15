package com.ecare.yjylaio.lechange.openapi.entity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.List;

public class SubAccountDeviceData implements Serializable {

    public static class Response {
        public SubAccountDeviceData.ResponseData data;

        public void parseData(JsonObject json) {
            Gson gson = new Gson();
            this.data = gson.fromJson(json.toString(),SubAccountDeviceData.ResponseData.class);
        }
    }

    public static class ResponseData implements Serializable {
        public int pageNo;
        public int pageSize;
        public int total;
        List<DeviceList> deviceLists;

        public static class DeviceList implements Serializable{
            public String deviceId;
            public String permission;
            List<ChannelList> channelLists;
        }

        public static class ChannelList implements  Serializable{
            public String channelId;
            public String permission;
        }
    }
}
