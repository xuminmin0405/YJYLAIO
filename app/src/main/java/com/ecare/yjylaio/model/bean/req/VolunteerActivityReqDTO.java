package com.ecare.yjylaio.model.bean.req;

/**
 * ProjectName: YJYLAged
 * Package: com.ecare.yjylaged.model.bean.req
 * ClassName: VolunteerActivityReqDTO
 * Description:
 * Author:
 * CreateDate: 2021/8/2 15:39
 * Version: 1.0
 */
public class VolunteerActivityReqDTO {

    /**
     * activityType :
     * pageNo : 0
     * pageSize : 0
     * street :
     */
    private String activityType;
    private int pageNo;
    private int pageSize;
    private String street;

    public VolunteerActivityReqDTO(String activityType, int pageNo, int pageSize, String street) {
        this.activityType = activityType;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.street = street;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
