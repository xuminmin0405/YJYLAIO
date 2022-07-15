package com.ecare.yjylaio.model.bean.req;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.model.bean.req
 * ClassName: JoinrActivityReqDTO
 * Description:
 * Author:
 * CreateDate: 2022/3/24 16:36
 * Version: 1.0
 */
public class JoinrActivityReqDTO {

    /**
     * activityId :
     * idNo :
     * userId :
     */
    private String activityId;
    private String userId;

    public JoinrActivityReqDTO(String activityId, String userId) {
        this.activityId = activityId;
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
