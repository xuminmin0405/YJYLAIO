package com.ecare.yjylaio.model.api;

import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.rsp.SelfAssessmentRspDTO;

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
public interface TransApiService {

    @GET("mzt_yl_assess_info_latest_to_ytj")
    Flowable<BaseResponse<SelfAssessmentRspDTO>> getSelfAssessment(@Query("code") String code, @Query("codeType") String codeType, @Query("requestorType") String requestorType, @Query("bizCode") String bizCode);
}
