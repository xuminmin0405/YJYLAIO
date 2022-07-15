package com.ecare.yjylaio.widght;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ecare.yjylaio.R;
import com.ecare.yjylaio.config.Constants;
import com.ecare.yjylaio.rtc.activity.AliRtcChatActivity;
import com.ecare.yjylaio.ui.activity.DoctorActivity;
import com.ecare.yjylaio.ui.activity.MainActivity;
import com.ecare.yjylaio.ui.activity.SelfAssessmentActivity;
import com.ecare.yjylaio.ui.activity.SelfAssessmentNewActivity;
import com.lxj.xpopup.core.CenterPopupView;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.widght
 * ClassName: SwipePromptPopupView
 * Description:
 * Author:
 * CreateDate: 2021/6/24 15:56
 * Version: 1.0
 */
public class SwipePromptPopupView extends CenterPopupView {

    //上下文
    private Context mContext;
    //Disposable
    private Disposable mSubscribe;
    //读卡标记
    private boolean isRead = true;
    private boolean isIdCard = true;
    //跳转类型
    private int mType;

    public SwipePromptPopupView(@NonNull Context context, int type) {
        super(context);
        mContext = context;
        mType = type;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_swipe_prompt;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        if (MainActivity.mReader == null) {
            ToastUtils.showShort("设备未打开");
        } else {
            mSubscribe = Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
                    while (isRead) {
                        String cardInfo = readCardInfo();
                        if (StringUtils.isEmpty(cardInfo) || StringUtils.isEmpty(splitCardInfo(cardInfo))) {
                            isIdCard = !isIdCard;
                        } else {
                            isRead = false;
                            e.onNext(cardInfo);
                        }
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            String idCard = splitCardInfo(s);
                            Intent intent = null;
                            if (mType == 1) {
                                intent = new Intent(mContext, AliRtcChatActivity.class);
                                intent.putExtra(Constants.IT_ID_CARD, idCard);
                                intent.putExtra(Constants.IT_NAME, s.split("\\|")[isIdCard ? 0 : 2]);
                            } else if (mType == 2) {
                                intent = new Intent(mContext, SelfAssessmentNewActivity.class);
                                intent.putExtra(Constants.IT_ID_CARD, "330121195812231127");
                            } else if (mType == 3) {
                                intent = new Intent(mContext, DoctorActivity.class);
                                intent.putExtra(Constants.IT_ID_CARD, idCard);
                                intent.putExtra(Constants.IT_NAME, s.split("\\|")[isIdCard ? 0 : 2]);
                            }
                            mContext.startActivity(intent);
                            dismiss();
                        }
                    });
        }
    }

    // 设置最大宽度，看需要而定，
    @Override
    protected int getMaxWidth() {
        return 0;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mSubscribe != null) {
            isRead = false;
            mSubscribe.dispose();
        }
    }

    /**
     * 读取卡片数据
     *
     * @return
     */
    private String readCardInfo() {
        try {
            if (MainActivity.mReader == null) {
                return "";
            }
            //切换非接CPU天线
            MainActivity.mReader.IDCardSet(isIdCard ? 1 : 0);
            Thread.sleep(100);
            return isIdCard ? MainActivity.mReaderAndroidUsb.ReadIDCardInfoB() : MainActivity.mReader.ReadSSCardInfo();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 截取卡片数据
     *
     * @param cardInfo 卡片数据
     * @return 身份证号
     */
    private String splitCardInfo(String cardInfo) {
        if (StringUtils.isEmpty(cardInfo)) {
            return "";
        }
        for (String s : cardInfo.split("\\|")) {
            if (!StringUtils.isEmpty(s) && RegexUtils.isIDCard18(s)) {
                return s;
            }
        }
        return "";
    }
}
