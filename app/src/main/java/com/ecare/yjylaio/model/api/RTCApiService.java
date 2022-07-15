package com.ecare.yjylaio.model.api;

import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.req.VideoDialogueCallRspDTO;
import com.ecare.yjylaio.model.bean.rsp.VideoDialogueTokenRspDTO;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/11.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * ProjectApiService
 */
public interface RTCApiService {

    @GET("auth/video_dialogue/appGetToken")
    Flowable<BaseResponse<VideoDialogueTokenRspDTO>> getRTCData(@Query("idCard") String idCard);

    @POST("auth/video_dialogue/callVideo")
    Flowable<BaseResponse<Boolean>> callVideo(@Body VideoDialogueCallRspDTO videoDialogueCallRspDTO);

    @GET("data_Visualization/entered_total")
    Flowable<BaseResponse<Integer>> getEnteredTotal();
}
