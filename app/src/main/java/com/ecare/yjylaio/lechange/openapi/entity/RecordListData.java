package com.ecare.yjylaio.lechange.openapi.entity;

import com.ecare.yjylaio.lechange.adapter.DeviceInnerRecordListAdapter;

import java.io.Serializable;
import java.util.List;

public class RecordListData implements Serializable {
    public String period;
    public List<RecordsData> recordsData;
    public DeviceInnerRecordListAdapter deviceInnerRecordListAdapters;
}
