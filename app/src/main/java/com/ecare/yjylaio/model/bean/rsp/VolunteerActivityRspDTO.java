package com.ecare.yjylaio.model.bean.rsp;

import java.util.Date;

/**
 * ProjectName: YJYLAged
 * Package: com.ecare.yjylaged.model.bean.rsp
 * ClassName: VolunteerActivityRspDTO
 * Description:
 * Author:
 * CreateDate: 2021/8/2 15:40
 * Version: 1.0
 */
public class VolunteerActivityRspDTO {

    /**
     * activityAddress :
     * activityContent :
     * activityEndTime :
     * activityImg :
     * activityName :
     * activityStatus : 0
     * activityTime :
     * activityType :
     * createTime :
     * createUser :
     * deleteTime :
     * deleteUser :
     * endSignTime :
     * id :
     * isComplete : true
     * isDelete : true
     * isJoin : 0
     * joinCount : 0
     * orgLinks :
     * remark :
     * startSignTime :
     * totalCount : 0
     * updateTime :
     * updateUser :
     */
    private String activityAddress;
    private String activityContent;
    private String activityEndTime;
    private String activityImg;
    private String activityName;
    private int activityStatus;
    private Date activityTime;
    private String activityType;
    private String createTime;
    private String createUser;
    private String deleteTime;
    private String deleteUser;
    private Date endSignTime;
    private String id;
    private boolean isComplete;
    private boolean isDelete;
    private int isJoin;
    private int joinCount;
    private String orgLinks;
    private String remark;
    private Date startSignTime;
    private int totalCount;
    private String updateTime;
    private String updateUser;

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Date getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(Date activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(String deleteUser) {
        this.deleteUser = deleteUser;
    }

    public Date getEndSignTime() {
        return endSignTime;
    }

    public void setEndSignTime(Date endSignTime) {
        this.endSignTime = endSignTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public int getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(int isJoin) {
        this.isJoin = isJoin;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public String getOrgLinks() {
        return orgLinks;
    }

    public void setOrgLinks(String orgLinks) {
        this.orgLinks = orgLinks;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getStartSignTime() {
        return startSignTime;
    }

    public void setStartSignTime(Date startSignTime) {
        this.startSignTime = startSignTime;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
