package com.mm.android.deviceaddmodule.mobilecommon.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mm.android.deviceaddmodule.R;
import com.mm.android.deviceaddmodule.mobilecommon.utils.UIUtils;
import com.mm.android.deviceaddmodule.mobilecommon.widget.linechart.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.decode.FileImageDecoder;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class CommonItem extends RelativeLayout {
    private View mTopLine;
    private View mBottomLine;
    private TextView mNameTv;
    private TextView mSubTv;
    private TextView mTitleTv;
    private ImageView mImageView;
    private ProgressBar mLoadingPb;
    private TextView mSwitchTv;

    private OnSwitchClickListener mListener;

    public CommonItem(Context context) {
        this(context, null);
    }

    public CommonItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_common_itme, this);
        initView();
        setListeners();
    }

    public TextView getTittle() {
        return mTitleTv;
    }

    private void initView() {
        mTopLine = findViewById(R.id.top_line);
        mBottomLine = findViewById(R.id.bottom_line);
        mNameTv = (TextView) findViewById(R.id.name_tv);
        mSubTv = (TextView) findViewById(R.id.sub_tv);
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mImageView = (ImageView) findViewById(R.id.img_iv);
        mLoadingPb = (ProgressBar) findViewById(R.id.loading_pb);
        mSwitchTv = (TextView) findViewById(R.id.switch_tv);
    }

    private void setListeners() {
        mSwitchTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCommonSwitchClick(CommonItem.this);
                }
            }
        });
    }

    public void setOnSwitchClickListener(OnSwitchClickListener listener) {
        mListener = listener;
    }


    public interface OnSwitchClickListener {
        void onCommonSwitchClick(View view);
    }


    /**
     * ????????????
     *
     * @param resId
     */
    public void setTitle(int resId) {
        if (mTitleTv == null || resId <= 0) {
            return;
        }
        mTitleTv.setText(resId);
    }

    /**
     * ????????????????????????????????????????????????drawable??????????????????l
     *
     * @param drawableLeft
     */
    @SuppressLint("ResourceType")
    public void setCompoundDrawableLeft(@DrawableRes int drawableLeft) {
        if (mTitleTv == null) {
            return;
        }
        mBottomLine.setVisibility(View.GONE);
        if (drawableLeft <= 0) {
            mTitleTv.setCompoundDrawablePadding(0);
            mTitleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            mTitleTv.setCompoundDrawablePadding((int) Utils.dp2px(13));
            mTitleTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, 0, 0, 0);
        }
    }

    /**
     * ????????????
     *
     * @param title
     */
    public void setTitle(String title) {
        if (mTitleTv == null || title == null) {
            return;
        }
        mTitleTv.setText(title);
    }

    /**
     * ????????????
     *
     * @param resId
     */
    public void setName(int resId) {
        if (mNameTv == null || resId <= 0) {
            return;
        }
        mNameTv.setText(resId);
        setNameVisible(true);
        setImageVisible(false);
        setLoadingVisible(false);
        setSwitchVisible(false);
    }

    /**
     * ????????????
     *
     * @param name
     */
    public void setName(String name) {
        if (mNameTv == null || name == null) {
            return;
        }
        mNameTv.setText(name);
        setNameVisible(true);
        setImageVisible(false);
        setLoadingVisible(false);
        setSwitchVisible(false);
    }

    /**
     * ????????????
     */
    public String getName() {
        if (mNameTv == null) {
            return "";
        }
        return mNameTv.getText().toString();
    }

    /**
     * ??????????????????
     *
     * @param visible
     */
    public void setSubVisible(boolean visible) {
        if (mSubTv == null) {
            return;
        }
        if (visible) {
            mSubTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.device_manager_icon_nextarrow, 0);
        } else {
            mSubTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * ?????????????????????
     *
     * @param visible
     */
    public void setLoadingVisible(boolean visible) {
        if (mLoadingPb == null) {
            return;
        }
        mLoadingPb.setVisibility(visible ? View.VISIBLE : View.GONE);
        if (visible) {
            setNameVisible(false);
            setSwitchVisible(false);
            setImageVisible(false);
        }
    }

    private void setNameVisible(boolean visible) {
        if (mNameTv == null) {
            return;
        }
        mNameTv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setSwitchVisible(boolean visible) {
        if (mSwitchTv == null) {
            return;
        }
        mSwitchTv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setImageVisible(boolean visible) {
        if (mImageView == null) {
            return;
        }
        mImageView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * ???????????????????????????
     *
     * @param visible
     */
    public void setTopLineVisible(boolean visible) {
        if (mTopLine == null) {
            return;
        }
        mTopLine.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * ???????????????????????????
     *
     * @param visible
     */
    public void setBottomLineVisible(boolean visible) {
        if (mBottomLine == null) {
            return;
        }
        mBottomLine.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setBottomLineLeftMargin(int dp) {
        if (mBottomLine == null) {
            return;
        }

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mBottomLine.getContext().getResources().getDisplayMetrics());
        setMargins(mBottomLine, px, 0, 0, 0);
    }

    public void setNameRightMargin(int dp){
        if (mNameTv == null) {
            return;
        }

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mNameTv.getContext().getResources().getDisplayMetrics());
        setMargins(mNameTv, 0, 0, px, 0);
    }


    /**
     * ?????????????????????
     */
    public void setItemNoAuthority() {
        setName(R.string.common_no_authority);
        setItemEnable(false);
        setTitleEnable(true);
    }

    public void setTitleEnable(boolean enable) {
        if (mTitleTv == null) {
            return;
        }
        mTitleTv.setEnabled(enable);
    }

    /**
     * ??????????????????
     */
    public void setItemEnable(boolean enable) {
//        setTitleEnable(enable);
//        setNameEnable(enable);
        setDevManagerItemEnable(enable, this, mSubTv);
//        setSwitchEnable(enable);
//        setSubVisible(enable);
        setClickable(enable);
    }

    public void setIteEnableWithoutClickEnable(boolean enable) {
        setDevManagerItemEnable(enable, this, null);
    }

    private void setDevManagerItemEnable(boolean enabled, ViewGroup viewGroup, TextView tv) {
        UIUtils.setEnabledSub(enabled, viewGroup);

        if (tv == null) {
            return;
        }
        if (enabled) {
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.device_manager_icon_nextarrow, 0);
        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * ?????????????????????
     */
    public void setItemClickable(boolean clickable) {
        setClickable(clickable);
        setSubVisible(clickable);
    }

    /**
     * ??????????????????
     */
    public void setItemOffLine() {
        setName(R.string.common_offline);
        setItemEnable(false);
    }

    /**
     * ????????????????????????
     *
     * @param url
     */
    public void setImage(String url, FileImageDecoder fileImageDecoder) {
        setImageVisible(true);
        if (!TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, mImageView,
                    new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUrl, View view) {

                            mLoadingPb.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUrl,
                                                      View view, Bitmap loadImage) {
                            mLoadingPb.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUrl, View view,
                                                    FailReason failReason) {
                            mImageView.setBackgroundResource(R.drawable.default_cover_small);
                            mLoadingPb.setVisibility(View.GONE);
                        }
                    }, fileImageDecoder);
        } else {
            mImageView.setBackgroundResource(R.drawable.default_cover_small);
        }
        setSwitchVisible(false);
        setNameVisible(false);
    }

    /**
     * ????????????????????????
     */
    public void setImageInVisible() {
        setImageVisible(false);
        setNameVisible(false);
        setSwitchVisible(false);
        setLoadingVisible(false);
    }

    /**
     * ??????????????????
     *
     * @param isSelected
     */
    public void setSwitchSelected(boolean isSelected) {
        if (mSwitchTv == null) {
            return;
        }
        mSwitchTv.setSelected(isSelected);
        setSwitchVisible(true);
        setNameVisible(false);
        setLoadingVisible(false);
        setSubVisible(false);
    }

    /**
     * ??????????????????
     */
    public boolean isSwitchSelected() {
        return mSwitchTv != null && mSwitchTv.isSelected();
    }

    /**
     * ???????????????????????????
     *
     * @param enable
     */
    public void setSwitchEnable(boolean enable) {
        if (mSwitchTv == null) {
            return;
        }
        mSwitchTv.setEnabled(enable);
    }

    /**
     * ??????????????????
     *
     * @param hasRedDot
     */
    public void setNameRedDot(boolean hasRedDot) {
        if (mNameTv == null) {
            return;
        }
        if (hasRedDot) {
            mNameTv.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.common_newmessage,
                    0);
        } else {
            mNameTv.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0);
        }
    }

    // ??????view ???margin???
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof MarginLayoutParams) {
            MarginLayoutParams p = (MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    //?????????????????????
    public void setNoArrowName(int resId) {
        setName(resId);
        setSubVisible(false);
        setLoadingVisible(false);
    }

    public void setRightDrawableCheck(boolean isCheck) {
        setName("");
        if (isCheck) {
            mNameTv.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.setting_icon_check,
                    0);
        } else {
            mNameTv.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    0,
                    0);
        }
    }
}
