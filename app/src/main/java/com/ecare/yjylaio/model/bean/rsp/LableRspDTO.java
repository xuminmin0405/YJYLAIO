package com.ecare.yjylaio.model.bean.rsp;

/**
 * ProjectName: YJYLAged
 * Package: com.ecare.yjylaged.model.bean.rsp
 * ClassName: LableRspDTO
 * Description:
 * Author:
 * CreateDate: 2021/8/3 14:39
 * Version: 1.0
 */
public class LableRspDTO {

    /**
     * applyStatus :
     * createTime :
     * createUser :
     * deleteTime :
     * deleteUser :
     * id :
     * img :
     * isApply : 0
     * isDelete : true
     * lableName :
     * requirement :
     * sortNum : 0
     * tagBackground :
     * tagBorderColor :
     * updateTime :
     * updateUser :
     */
    private String applyStatus;
    private String createTime;
    private String createUser;
    private String deleteTime;
    private String deleteUser;
    private String id;
    private String img;
    private int isApply;
    private boolean isDelete;
    private String lableName;
    private String requirement;
    private int sortNum;
    private String tagBackground;
    private String tagBorderColor;
    private String updateTime;
    private String updateUser;

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIsApply() {
        return isApply;
    }

    public void setIsApply(int isApply) {
        this.isApply = isApply;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getTagBackground() {
        return tagBackground;
    }

    public void setTagBackground(String tagBackground) {
        this.tagBackground = tagBackground;
    }

    public String getTagBorderColor() {
        return tagBorderColor;
    }

    public void setTagBorderColor(String tagBorderColor) {
        this.tagBorderColor = tagBorderColor;
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
