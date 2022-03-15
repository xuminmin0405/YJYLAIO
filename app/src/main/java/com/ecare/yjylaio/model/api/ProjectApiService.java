package com.ecare.yjylaio.model.api;

import com.ecare.yjylaio.model.bean.BasePaging;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.rsp.TrainingVideoRspDTO;
import com.ecare.yjylaio.model.bean.rsp.UserHealthDataRspDTO;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * AndroidProject
 * <p>
 * Created by xuminmin on 2018/12/11.
 * Email: iminminxu@gmail.com
 * Copyright © 2018年 Hangzhou Gravity Cyberinfo. All rights reserved.
 * ProjectApiService
 */
public interface ProjectApiService {

    @GET("training_video/pagination")
    Flowable<BaseResponse<BasePaging<TrainingVideoRspDTO>>> getTrainingVideo(@Query("category") String category, @Query("name") String name, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    @GET("UserHealth/findHealthData")
    Flowable<BaseResponse<UserHealthDataRspDTO>> getHealthData(@Query("idCard") String idCard);

    @GET("user_device/getYTJDevice")
    Flowable<BaseResponse<String>> getYTJDevice();
}
