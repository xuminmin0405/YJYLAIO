package com.ecare.yjylaio.rtc.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alivc.rtc.AliRtcAuthInfo;
import com.alivc.rtc.AliRtcEngine;
import com.alivc.rtc.AliRtcEngineEventListener;
import com.alivc.rtc.AliRtcEngineNotify;
import com.alivc.rtc.AliRtcRemoteUserInfo;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.model.api.RTCApi;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.req.VideoDialogueCallRspDTO;
import com.ecare.yjylaio.model.bean.rsp.VideoDialogueTokenRspDTO;
import com.ecare.yjylaio.model.rxjava.CommonSubscriber;
import com.ecare.yjylaio.model.rxjava.RxUtils;
import com.ecare.yjylaio.rtc.adapter.BaseRecyclerViewAdapter;
import com.ecare.yjylaio.rtc.adapter.ChartUserAdapter;
import com.ecare.yjylaio.rtc.bean.ChartUserBean;

import org.webrtc.sdk.SophonSurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.alivc.rtc.AliRtcEngine.AliRtcRenderMode.AliRtcRenderModeAuto;
import static com.alivc.rtc.AliRtcEngine.AliRtcVideoTrack.AliRtcVideoTrackBoth;
import static com.alivc.rtc.AliRtcEngine.AliRtcVideoTrack.AliRtcVideoTrackCamera;
import static com.alivc.rtc.AliRtcEngine.AliRtcVideoTrack.AliRtcVideoTrackNo;
import static com.alivc.rtc.AliRtcEngine.AliRtcVideoTrack.AliRtcVideoTrackScreen;
import static org.webrtc.alirtcInterface.ErrorCodeEnum.ERR_ICE_CONNECTION_HEARTBEAT_TIMEOUT;
import static org.webrtc.alirtcInterface.ErrorCodeEnum.ERR_SESSION_REMOVED;

/**
 * ??????????????????activity
 */
public class AliRtcChatActivity extends AppCompatActivity {
    private static final String TAG = AliRtcChatActivity.class.getName();


    public static final int CAMERA = 1001;
    public static final int SCREEN = 1002;

    public static final String[] VIDEO_INFO_KEYS = {"Width", "Height", "FPS", "LossRate"};

    private static final int PERMISSION_REQ_ID = 0x0002;

    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * ???????????????view
     */
    private SophonSurfaceView mLocalView;
    /**
     * SDK?????????????????????????????????????????????
     */
    private AliRtcEngine mAliRtcEngine;
    /**
     * ???????????????Intent
     */
    private Intent mForeServiceIntent;

    /**
     * ????????????User???Adapter
     */
    private ChartUserAdapter mUserListAdapter;

    private VideoDialogueTokenRspDTO mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alirtc_activity_chat);
        // ?????????????????????view
        initView();
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[2], PERMISSION_REQ_ID)) {
            // ???????????????????????????????????????
            initRTCEngineAndStartPreview();
        }
    }

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                showToast("Need permissions " + Manifest.permission.RECORD_AUDIO +
                        "/" + Manifest.permission.CAMERA + "/" + Manifest.permission.WRITE_EXTERNAL_STORAGE);
                finish();
                return;
            }
            initRTCEngineAndStartPreview();
        }
    }

    private void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        findViewById(R.id.iv_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLocalView = findViewById(R.id.sf_local_view);
        // ????????????User???Adapter
        mUserListAdapter = new ChartUserAdapter();
        RecyclerView chartUserListView = findViewById(R.id.chart_content_userlist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        chartUserListView.setLayoutManager(layoutManager);
        chartUserListView.addItemDecoration(new BaseRecyclerViewAdapter.DividerGridItemDecoration(
                getResources().getDrawable(R.drawable.chart_content_userlist_item_divider)));
        DefaultItemAnimator anim = new DefaultItemAnimator();
        anim.setSupportsChangeAnimations(false);
        chartUserListView.setItemAnimator(anim);
        chartUserListView.setAdapter(mUserListAdapter);
        mUserListAdapter.setOnSubConfigChangeListener(mOnSubConfigChangeListener);
    }

    private void initRTCEngineAndStartPreview() {

        //?????????????????????H5
        AliRtcEngine.setH5CompatibleMode(1);
        // ?????????????????????
        if (mAliRtcEngine == null) {
            //?????????,???????????????????????????
            mAliRtcEngine = AliRtcEngine.getInstance(getApplicationContext());
            //???????????????????????????
            mAliRtcEngine.setRtcEngineEventListener(mEventListener);
            //?????????????????????????????????
            mAliRtcEngine.setRtcEngineNotify(mEngineNotify);
            AliRtcEngine.AliRtcVideoEncoderConfiguration aliRtcVideoEncoderConfiguration = new AliRtcEngine.AliRtcVideoEncoderConfiguration();
            aliRtcVideoEncoderConfiguration.rotationMode = AliRtcEngine.AliRtcRotationMode.AliRtcRotationMode_270;
            mAliRtcEngine.setVideoEncoderConfiguration(aliRtcVideoEncoderConfiguration);
            // ?????????????????????
            initLocalView();
            //????????????
            startPreview();
            CommonSubscriber<VideoDialogueTokenRspDTO> commonSubscriber = RTCApi.getInstance().getApiService()
                    .getRTCData(getIntent().getStringExtra(Constants.IT_ID_CARD))
                    .compose(RxUtils.<BaseResponse<VideoDialogueTokenRspDTO>>rxSchedulerHelper())
                    .compose(RxUtils.<VideoDialogueTokenRspDTO>handleResult())
                    .subscribeWith(new CommonSubscriber<VideoDialogueTokenRspDTO>() {
                        @Override
                        public void onNext(VideoDialogueTokenRspDTO data) {
                            if (data == null) {
                                return;
                            }
                            mData = data;
                            //????????????
                            joinChannel();
                        }
                    });
        }
    }

    private void startPreview() {
        if (mAliRtcEngine == null) {
            return;
        }
        try {
            mAliRtcEngine.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????????????????view
     */
    private void initLocalView() {
        mLocalView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        mLocalView.setZOrderOnTop(false);
        mLocalView.setZOrderMediaOverlay(false);
        AliRtcEngine.AliRtcVideoCanvas aliVideoCanvas = new AliRtcEngine.AliRtcVideoCanvas();
        aliVideoCanvas.view = mLocalView;
        aliVideoCanvas.renderMode = AliRtcRenderModeAuto;
        aliVideoCanvas.rotationMode = AliRtcEngine.AliRtcRotationMode.AliRtcRotationMode_90;
        if (mAliRtcEngine != null) {
            mAliRtcEngine.setLocalViewConfig(aliVideoCanvas, AliRtcVideoTrackCamera);
        }
    }

    private void joinChannel() {
        if (mAliRtcEngine == null) {
            return;
        }
        //?????????????????????????????????????????????????????????:https://help.aliyun.com/document_detail/159037.html?spm=a2c4g.11186623.6.580.2a223b03USksim
        AliRtcAuthInfo userInfo = new AliRtcAuthInfo();
        userInfo.setAppId(mData.getAppID()); //??????AppID
        userInfo.setNonce(mData.getNonce()); //??????????????????????????????AK-
        userInfo.setGslb(new String[]{"https://rgslb.rtc.aliyuncs.com"});  //??????????????????????????????["https://rgslb.rtc.aliyuncs.com"]
        userInfo.setTimestamp(mData.getTimeStamp()); //?????????????????????
        userInfo.setToken(mData.getToken());     //????????????Token
        userInfo.setChannelId(mData.getChannelId()); //??????ID
        userInfo.setUserId(mData.getUserId());  //??????ID

        // ?????????????????????1:???????????? ??????2:?????????
        mAliRtcEngine.joinChannel(userInfo, getIntent().getStringExtra(Constants.IT_NAME));
        CommonSubscriber<Boolean> commonSubscriber = RTCApi.getInstance().getApiService()
                .callVideo(new VideoDialogueCallRspDTO(1, mData.getChannelId(), mData.getDoctorId(), getIntent().getStringExtra(Constants.IT_NAME)))
                .compose(RxUtils.<BaseResponse<Boolean>>rxSchedulerHelper())
                .compose(RxUtils.<Boolean>handleResult())
                .subscribeWith(new CommonSubscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean data) {

                    }
                });
    }

    @Override
    public void finish() {
        super.finish();
        if (mData == null) {
            return;
        }
        CommonSubscriber<Boolean> commonSubscriber = RTCApi.getInstance().getApiService()
                .callVideo(new VideoDialogueCallRspDTO(2, mData.getChannelId(), mData.getDoctorId(), getIntent().getStringExtra(Constants.IT_NAME)))
                .compose(RxUtils.<BaseResponse<Boolean>>rxSchedulerHelper())
                .compose(RxUtils.<Boolean>handleResult())
                .subscribeWith(new CommonSubscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean data) {

                    }
                });
    }

    private void addRemoteUser(String uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //????????????????????????
                if (null == mAliRtcEngine) {
                    return;
                }
                AliRtcRemoteUserInfo remoteUserInfo = mAliRtcEngine.getUserInfo(uid);
                if (remoteUserInfo != null) {
                    mUserListAdapter.updateData(convertRemoteUserToUserData(remoteUserInfo), true);
                }
            }
        });
    }

    private void removeRemoteUser(String uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUserListAdapter.removeData(uid, true);
            }
        });
    }

    private void updateRemoteDisplay(String uid, AliRtcEngine.AliRtcAudioTrack at, AliRtcEngine.AliRtcVideoTrack vt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null == mAliRtcEngine) {
                    return;
                }
                AliRtcRemoteUserInfo remoteUserInfo = mAliRtcEngine.getUserInfo(uid);
                // ???????????????????????????????????????????????????????????????????????????????????????
                if (remoteUserInfo == null) {
                    // remote user exit room
                    Log.e(TAG, "updateRemoteDisplay remoteUserInfo = null, uid = " + uid);
                    return;
                }
                //change
                AliRtcEngine.AliRtcVideoCanvas cameraCanvas = remoteUserInfo.getCameraCanvas();
                AliRtcEngine.AliRtcVideoCanvas screenCanvas = remoteUserInfo.getScreenCanvas();
                //????????????
                if (vt == AliRtcVideoTrackNo) {
                    //???????????????
                    cameraCanvas = null;
                    screenCanvas = null;
                } else if (vt == AliRtcVideoTrackCamera) {
                    //?????????
                    screenCanvas = null;
                    cameraCanvas = createCanvasIfNull(cameraCanvas);
                    //SDK???????????????????????????view
                    mAliRtcEngine.setRemoteViewConfig(cameraCanvas, uid, AliRtcVideoTrackCamera);
                } else if (vt == AliRtcVideoTrackScreen) {
                    //?????????
                    cameraCanvas = null;
                    screenCanvas = createCanvasIfNull(screenCanvas);
                    //SDK???????????????????????????view
                    mAliRtcEngine.setRemoteViewConfig(screenCanvas, uid, AliRtcVideoTrackScreen);
                } else if (vt == AliRtcVideoTrackBoth) {
                    //??????
                    cameraCanvas = createCanvasIfNull(cameraCanvas);
                    //SDK???????????????????????????view
                    mAliRtcEngine.setRemoteViewConfig(cameraCanvas, uid, AliRtcVideoTrackCamera);
                    screenCanvas = createCanvasIfNull(screenCanvas);
                    //SDK???????????????????????????view
                    mAliRtcEngine.setRemoteViewConfig(screenCanvas, uid, AliRtcVideoTrackScreen);
                } else {
                    return;
                }
                ChartUserBean chartUserBean = convertRemoteUserInfo(remoteUserInfo, cameraCanvas, screenCanvas);
                mUserListAdapter.updateData(chartUserBean, true);

            }
        });
    }

    private ChartUserBean convertRemoteUserToUserData(AliRtcRemoteUserInfo remoteUserInfo) {
        String uid = remoteUserInfo.getUserID();
        ChartUserBean ret = mUserListAdapter.createDataIfNull(uid);
        ret.mUserId = uid;
        ret.mUserName = remoteUserInfo.getDisplayName();
        ret.mIsCameraFlip = false;
        ret.mIsScreenFlip = false;
        return ret;
    }

    private AliRtcEngine.AliRtcVideoCanvas createCanvasIfNull(AliRtcEngine.AliRtcVideoCanvas canvas) {
        if (canvas == null || canvas.view == null) {
            //??????canvas???Canvas???SophonSurfaceView??????????????????
            canvas = new AliRtcEngine.AliRtcVideoCanvas();
            SophonSurfaceView surfaceView = new SophonSurfaceView(this);
            surfaceView.setZOrderOnTop(true);
            surfaceView.setZOrderMediaOverlay(true);
            canvas.view = surfaceView;
            //renderMode?????????????????????Auto???Stretch???Fill???Crop???????????????Auto?????????
            canvas.renderMode = AliRtcRenderModeAuto;
        }
        return canvas;
    }

    private ChartUserBean convertRemoteUserInfo(AliRtcRemoteUserInfo remoteUserInfo,
                                                AliRtcEngine.AliRtcVideoCanvas cameraCanvas,
                                                AliRtcEngine.AliRtcVideoCanvas screenCanvas) {
        String uid = remoteUserInfo.getUserID();
        ChartUserBean ret = mUserListAdapter.createDataIfNull(uid);
        ret.mUserId = remoteUserInfo.getUserID();

        ret.mUserName = remoteUserInfo.getDisplayName();

        ret.mCameraSurface = cameraCanvas != null ? cameraCanvas.view : null;
        ret.mIsCameraFlip = cameraCanvas != null && cameraCanvas.mirrorMode == AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllEnabled;

        ret.mScreenSurface = screenCanvas != null ? screenCanvas.view : null;
        ret.mIsScreenFlip = screenCanvas != null && screenCanvas.mirrorMode == AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllEnabled;

        return ret;
    }

    /**
     * ????????????????????????????????????
     *
     * @param error ?????????
     */
    private void processOccurError(int error) {
        switch (error) {
            case ERR_ICE_CONNECTION_HEARTBEAT_TIMEOUT:
            case ERR_SESSION_REMOVED:
                noSessionExit(error);
                break;
            default:
                break;
        }
    }

    /**
     * ????????????
     *
     * @param error ?????????
     */
    private void noSessionExit(int error) {
        runOnUiThread(() -> new AlertDialog.Builder(AliRtcChatActivity.this)
                .setTitle("ErrorCode : " + error)
                .setMessage("??????????????????????????????")
                .setPositiveButton("??????", (dialog, which) -> {
                    dialog.dismiss();
                    onBackPressed();
                })
                .create()
                .show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAliRtcEngine != null) {
            mAliRtcEngine.destroy();
        }
    }

    /**
     * ????????????????????????(???????????????????????????)
     */
    private AliRtcEngineEventListener mEventListener = new AliRtcEngineEventListener() {

        /**
         * ?????????????????????
         * @param result ?????????
         */
        @Override
        public void onJoinChannelResult(int result, String channel, int elapsed) {
            runOnUiThread(() -> {
                if (result == 0) {
                    showToast("??????????????????");
                } else {
                    showToast("?????????????????? ?????????: " + result);
                }
            });
        }

        /**
         * ?????????????????????
         * @param error ?????????
         */
        @Override
        public void onOccurError(int error, String message) {
            super.onOccurError(error, message);
            //????????????
            processOccurError(error);
        }

    };

    /**
     * SDK????????????(???????????????????????????)
     */
    private AliRtcEngineNotify mEngineNotify = new AliRtcEngineNotify() {

        /**
         * ????????????????????????
         * @param uid userId
         */
        @Override
        public void onRemoteUserOnLineNotify(String uid, int elapsed) {
            addRemoteUser(uid);
        }

        /**
         * ????????????????????????
         * @param uid userId
         */
        @Override
        public void onRemoteUserOffLineNotify(String uid, AliRtcEngine.AliRtcUserOfflineReason reason) {
            removeRemoteUser(uid);
        }

        /**
         * ??????????????????????????????????????????
         * @param s userid
         * @param aliRtcAudioTrack ?????????
         * @param aliRtcVideoTrack ?????????
         */
        @Override
        public void onRemoteTrackAvailableNotify(String s, AliRtcEngine.AliRtcAudioTrack aliRtcAudioTrack,
                                                 AliRtcEngine.AliRtcVideoTrack aliRtcVideoTrack) {
            updateRemoteDisplay(s, aliRtcAudioTrack, aliRtcVideoTrack);
        }
    };

    private ChartUserAdapter.OnSubConfigChangeListener mOnSubConfigChangeListener = new ChartUserAdapter.OnSubConfigChangeListener() {
        @Override
        public void onFlipView(String uid, int flag, boolean flip) {
            AliRtcRemoteUserInfo userInfo = mAliRtcEngine.getUserInfo(uid);
            switch (flag) {
                case CAMERA:
                    if (userInfo != null) {
                        AliRtcEngine.AliRtcVideoCanvas cameraCanvas = userInfo.getCameraCanvas();
                        if (cameraCanvas != null) {
                            cameraCanvas.mirrorMode = flip ? AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllEnabled : AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllDisable;
                            mAliRtcEngine.setRemoteViewConfig(cameraCanvas, uid, AliRtcVideoTrackCamera);
                        }
                    }
                    break;
                case SCREEN:
                    if (userInfo != null) {
                        AliRtcEngine.AliRtcVideoCanvas screenCanvas = userInfo.getScreenCanvas();
                        if (screenCanvas != null) {
                            screenCanvas.mirrorMode = flip ? AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllEnabled : AliRtcEngine.AliRtcRenderMirrorMode.AliRtcRenderMirrorModeAllDisable;
                            mAliRtcEngine.setRemoteViewConfig(screenCanvas, uid, AliRtcVideoTrackScreen);
                        }
                    }
                    break;
            }
        }

        @Override
        public void onShowVideoInfo(String uid, int flag) {
        }
    };
}
