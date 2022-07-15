package com.ecare.yjylaio.model.bean.rsp;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.model.bean.rsp
 * ClassName: VideoDialogueTokenRspDTO
 * Description:
 * Author:
 * CreateDate: 2022/3/22 14:29
 * Version: 1.0
 */
public class VideoDialogueTokenRspDTO {

    /**
     * appID :
     * appKey :
     * channelId :
     * doctorId :
     * nonce :
     * timeStamp : 0
     * token :
     * userId :
     */
    private String appID;
    private String appKey;
    private String channelId;
    private String doctorId;
    private String nonce;
    private long timeStamp;
    private String token;
    private String userId;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
