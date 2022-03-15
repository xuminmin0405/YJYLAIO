package com.ecare.yjylaio.model.bean.req;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.model.bean.req
 * ClassName: NeteaseFaceInfoReqDTO
 * Description:
 * Author:
 * CreateDate: 2021/7/14 14:53
 * Version: 1.0
 */
public class NeteaseFaceInfoReqDTO {

    /**
     * idCard :
     * userName :
     */
    private String idCard;
    private String userName;

    public NeteaseFaceInfoReqDTO(String idCard, String userName) {
        this.idCard = idCard;
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
