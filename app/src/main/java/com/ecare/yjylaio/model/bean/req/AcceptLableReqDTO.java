package com.ecare.yjylaio.model.bean.req;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.model.bean.req
 * ClassName: AcceptLableReqDTO
 * Description:
 * Author:
 * CreateDate: 2022/3/24 16:35
 * Version: 1.0
 */
public class AcceptLableReqDTO {

    /**
     * lableId :
     * userId :
     */
    private String lableId;
    private String userId;

    public AcceptLableReqDTO(String lableId, String userId) {
        this.lableId = lableId;
        this.userId = userId;
    }

    public String getLableId() {
        return lableId;
    }

    public void setLableId(String lableId) {
        this.lableId = lableId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
