package com.ecare.yjylaio.lechange.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.ecare.yjylaio.R;
import com.ecare.yjylaio.lechange.adapter.DeviceListAdapter;
import com.ecare.yjylaio.lechange.openapi.ClassInstanceManager;
import com.ecare.yjylaio.lechange.openapi.DeviceListService;
import com.ecare.yjylaio.lechange.openapi.DeviceSubAccountListService;
import com.ecare.yjylaio.lechange.openapi.IGetDeviceInfoCallBack;
import com.ecare.yjylaio.lechange.openapi.MethodConst;
import com.ecare.yjylaio.lechange.openapi.entity.DeviceDetailListData;
import com.ecare.yjylaio.lechange.view.LcPullToRefreshRecyclerView;
import com.lechange.pulltorefreshlistview.Mode;
import com.lechange.pulltorefreshlistview.PullToRefreshBase;
import com.mm.android.deviceaddmodule.LCDeviceEngine;
import com.mm.android.deviceaddmodule.mobilecommon.utils.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.mm.android.deviceaddmodule.mobilecommon.route.RoutePathManager.ActivityPath.DeviceListActivityPath;

@Route(path = DeviceListActivityPath)
public class DeviceListActivity extends Activity implements IGetDeviceInfoCallBack.ISubAccountDevice< DeviceDetailListData.Response>, PullToRefreshBase.OnRefreshListener2, View.OnClickListener {
    private static final String TAG = DeviceListActivity.class.getSimpleName();
    private LcPullToRefreshRecyclerView deviceList;
    private RecyclerView mRecyclerView;
    private List<DeviceDetailListData.ResponseData.DeviceListBean> datas = new ArrayList<>();
    private DeviceListAdapter deviceListAdapter;
    private LinearLayout llAdd;
    private LinearLayout llBack;
    private RelativeLayout rlNoDevice;
    //乐橙分页index
    public long baseBindId = -1;
    //开放平台分页index
    public long openBindId = -1;
    int pageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        initView();
    }

    private void initView() {
        llAdd = findViewById(R.id.ll_add);
        llBack = findViewById(R.id.ll_back);
        rlNoDevice = findViewById(R.id.rl_no_device);
        deviceList = findViewById(R.id.device_list);
        deviceList.setOnRefreshListener(this);
        llAdd.setOnClickListener(this);
        llBack.setOnClickListener(this);
        refreshState(false);
        mRecyclerView = deviceList.getRefreshableView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(DeviceListActivity.this));
        deviceListAdapter = new DeviceListAdapter(DeviceListActivity.this, datas);
        mRecyclerView.setAdapter(deviceListAdapter);
        deviceListAdapter.setOnItemClickListener(new DeviceListAdapter.OnItemClickListener() {
            @Override
            public void onSettingClick(int position) {
                if (datas.size() == 0) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(MethodConst.ParamConst.deviceDetail, datas.get(position));
                bundle.putString(MethodConst.ParamConst.fromList, MethodConst.ParamConst.fromList);
                Intent intent = new Intent(DeviceListActivity.this, DeviceDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onDetailClick(int position) {
                if (datas.size() == 0) {
                    return;
                }
                if (!datas.get(position).status.equals("online")) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(MethodConst.ParamConst.deviceDetail, datas.get(position));
                Intent intent = new Intent(DeviceListActivity.this, DeviceOnlineMediaPlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onChannelClick(int outPosition, int innerPosition) {
                if (datas.size() == 0) {
                    return;
                }
                if (!datas.get(outPosition).channels.get(innerPosition).status.equals("online")) {
                    return;
                }
                Bundle bundle = new Bundle();
                DeviceDetailListData.ResponseData.DeviceListBean deviceListBean = datas.get(outPosition);
                deviceListBean.checkedChannel = innerPosition;
                bundle.putSerializable(MethodConst.ParamConst.deviceDetail, deviceListBean);
                Intent intent = new Intent(DeviceListActivity.this, DeviceOnlineMediaPlayActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshMode(Mode.PULL_FROM_START);
                refreshState(true);
            }
        }, 200);
    }


   /* public void deviceList(DeviceDetailListData.Response responseData) {
        if (isDestroyed()) {
            return;
        }
        refreshState(false);
        if (responseData.baseBindId != -1) {
            baseBindId = responseData.baseBindId;
        }
        if (responseData.openBindId != -1) {
            openBindId = responseData.openBindId;
        }
        if (responseData.data != null && responseData.data.deviceList != null && responseData.data.deviceList.size() != 0) {
            Iterator<DeviceDetailListData.ResponseData.DeviceListBean> iterator =  responseData.data.deviceList.iterator();
            while(iterator.hasNext()){
                DeviceDetailListData.ResponseData.DeviceListBean next = iterator.next();
                if (next.channels.size() == 0 && !next.catalog.contains("NVR")) {
                    // 使用迭代器中的remove()方法,可以删除元素.
                    iterator.remove();
                }
            }
        }
        //没有数据
        if ((responseData.data == null || responseData.data.deviceList == null || responseData.data.deviceList.size() == 0) && datas.size() == 0) {
            //本次未拉到数据且上次也没有数据
            rlNoDevice.setVisibility(View.VISIBLE);
            deviceList.setVisibility(View.GONE);
        } else {
            if ((responseData.data == null || responseData.data.deviceList == null || responseData.data.deviceList.size() == 0)) {
                return;
            }
            rlNoDevice.setVisibility(View.GONE);
            deviceList.setVisibility(View.VISIBLE);
            datas.addAll(responseData.data.deviceList);
            deviceListAdapter.notifyDataSetChanged();
            if (datas.size() >= DeviceListService.pageSize) {
                refreshMode(Mode.BOTH);
            } else {
                refreshMode(Mode.PULL_FROM_START);
            }
        }
    }*/


    @Override
    public void DeviceList(DeviceDetailListData.Response responseData) {
        if (isDestroyed()) {
            return;
        }
        refreshState(false);
        if (responseData.baseBindId != -1) {
            baseBindId = responseData.baseBindId;
        }
        if (responseData.openBindId != -1) {
            openBindId = responseData.openBindId;
        }
        if (responseData.data != null && responseData.data.deviceList != null && responseData.data.deviceList.size() != 0) {
            Iterator<DeviceDetailListData.ResponseData.DeviceListBean> iterator =  responseData.data.deviceList.iterator();
            while(iterator.hasNext()){
                DeviceDetailListData.ResponseData.DeviceListBean next = iterator.next();
                if (next.channels!=null&&next.channels.size() == 0 && !next.catalog.contains("NVR")) {
                    // 使用迭代器中的remove()方法,可以删除元素.
                    iterator.remove();
                }
            }
        }
        //没有数据
        if ((responseData.data == null || responseData.data.deviceList == null || responseData.data.deviceList.size() == 0) && datas.size() == 0) {
            //本次未拉到数据且上次也没有数据
            rlNoDevice.setVisibility(View.VISIBLE);
            deviceList.setVisibility(View.GONE);
        } else {
            if ((responseData.data == null || responseData.data.deviceList == null || responseData.data.deviceList.size() == 0)) {
                return;
            }
            rlNoDevice.setVisibility(View.GONE);
            deviceList.setVisibility(View.VISIBLE);
            datas.addAll(responseData.data.deviceList);
            deviceListAdapter.notifyDataSetChanged();
            if (datas.size() >= DeviceListService.pageSize) {
                refreshMode(Mode.BOTH);
            } else {
                refreshMode(Mode.PULL_FROM_START);
            }
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (isDestroyed()) {
            return;
        }
        refreshState(false);
        LogUtil.errorLog(TAG, "error", throwable);
        Toast.makeText(DeviceListActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase) {
        pageNum = 1;
        getDeviceList(false);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase) {
        pageNum = pageNum+1;
        getDeviceList(true);
    }

    private void getDeviceList(boolean isLoadMore) {
        if (!isLoadMore) {
            baseBindId = -1;
            openBindId = -1;
            datas.clear();
        }
        DeviceSubAccountListService deviceSubAccountListService = ClassInstanceManager.newInstance().getDeviceSubAccountListService();
        deviceSubAccountListService.getSubAccountDeviceList(pageNum,this);

      /*  DeviceListService deviceVideoService = ClassInstanceManager.newInstance().getDeviceListService();
        DeviceListData deviceListData = new DeviceListData();
        deviceListData.data.openBindId = this.openBindId;
        deviceListData.data.baseBindId = this.baseBindId;
        deviceVideoService.deviceBaseList(deviceListData, DeviceListActivity.this);*/
    }

    private void refreshState(boolean refresh) {
        if (refresh) {
            deviceList.setRefreshing(true);
        } else {
            deviceList.onRefreshComplete();
        }
    }

    private void refreshMode(Mode mode) {
        deviceList.setMode(mode);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_back) {
            finish();
        } else if (id == R.id.ll_add) {
            try {
                LCDeviceEngine.newInstance().addDevice(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
