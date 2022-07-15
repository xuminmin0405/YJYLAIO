package com.ecare.yjylaio.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.ecare.yjylaio.R;
import com.ecare.yjylaio.base.SimpleActivity;
import com.ecare.yjylaio.config.Constants;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.ui.activity
 * ClassName: VideoPlayerActivity
 * Description:
 * Author:
 * CreateDate: 2022/3/21 16:06
 * Version: 1.0
 */
public class VideoPlayerActivity extends SimpleActivity {

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    private OrientationUtils orientationUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.act_video_player;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        init();
    }

    private void init() {
        videoPlayer.setUp(getIntent().getStringExtra(Constants.IT_URL), true, getIntent().getStringExtra(Constants.IT_TITLE));
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    @Override
    protected void doBusiness() {

    }
}
