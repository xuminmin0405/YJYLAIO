package com.ecare.yjylaio.model.bean.req;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.model.bean.req
 * ClassName: VideoDialogueCallRspDTO
 * Description:
 * Author:
 * CreateDate: 2022/3/22 14:31
 * Version: 1.0
 */
public class VideoDialogueCallRspDTO {

    /**
     * callType : 0
     * channelId :
     * doctorId :
     * userName :
     */
    private int callType;
    private String channelId;
    private String doctorId;
    private String userName;

    public VideoDialogueCallRspDTO(int callType, String channelId, String doctorId, String userName) {
        this.callType = callType;
        this.channelId = channelId;
        this.doctorId = doctorId;
        this.userName = userName;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
