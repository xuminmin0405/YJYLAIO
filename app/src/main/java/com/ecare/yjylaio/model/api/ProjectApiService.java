package com.ecare.yjylaio.model.api;

import com.ecare.yjylaio.model.bean.BasePaging;
import com.ecare.yjylaio.model.bean.BaseResponse;
import com.ecare.yjylaio.model.bean.req.AcceptLableReqDTO;
import com.ecare.yjylaio.model.bean.req.InsSelfAssessmentReqDTO;
import com.ecare.yjylaio.model.bean.req.JoinrActivityReqDTO;
import com.ecare.yjylaio.model.bean.req.VolunteerActivityReqDTO;
import com.ecare.yjylaio.model.bean.rsp.LableRspDTO;
import com.ecare.yjylaio.model.bean.rsp.TrainingVideoRspDTO;
import com.ecare.yjylaio.model.bean.rsp.UserHealthDataRspDTO;
import com.ecare.yjylaio.model.bean.rsp.VolunteerActivityRspDTO;

import java.util.List;

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
public interface ProjectApiService {

    @GET("training_video/pagination")
    Flowable<BaseResponse<BasePaging<TrainingVideoRspDTO>>> getTrainingVideo(@Query("category") String category, @Query("name") String name, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize);

    @GET("UserHealth/findHealthData")
    Flowable<BaseResponse<UserHealthDataRspDTO>> getHealthData(@Query("idCard") String idCard);

    @GET("user_device/getYTJDevice")
    Flowable<BaseResponse<String>> getYTJDevice();

    @POST("insSelfAssessment/addInsSelfAssessment")
    Flowable<BaseResponse<Boolean>> selfAssessmentSubmit(@Body InsSelfAssessmentReqDTO insSelfAssessmentReqDTO);

    @POST("helpHappy/findActivityPage")
    Flowable<BaseResponse<BasePaging<VolunteerActivityRspDTO>>> getActivity(@Body VolunteerActivityReqDTO volunteerActivityReqDTO);

    @GET("helpHappy/findHelpLable")
    Flowable<BaseResponse<List<LableRspDTO>>> getLabel();

    @GET("helpHappy/findHelpLableByIdWithUserIsApply")
    Flowable<BaseResponse<LableRspDTO>> getLabelDetail(@Query("id") String id, @Query("userId") String userId);

    @GET("helpHappy/findActivityInfoByUserId")
    Flowable<BaseResponse<VolunteerActivityRspDTO>> getHelpDetail(@Query("id") String id, @Query("userId") String userId);

    @POST("helpHappy/acceptLable")
    Flowable<BaseResponse<Boolean>> acceptLabel(@Body AcceptLableReqDTO acceptLableReqDTO);

    @POST("helpHappy/jionVolunteerActivity")
    Flowable<BaseResponse<Boolean>> joinVolunteerActivity(@Body JoinrActivityReqDTO joinrActivityReqDTO);
}
