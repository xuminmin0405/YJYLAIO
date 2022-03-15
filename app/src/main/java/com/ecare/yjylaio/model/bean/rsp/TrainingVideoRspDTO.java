package com.ecare.yjylaio.model.bean.rsp;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.model.bean.rsp
 * ClassName: TrainingVideoRspDTO
 * Description:
 * Author:
 * CreateDate: 2021/6/18 13:54
 * Version: 1.0
 */
public class TrainingVideoRspDTO {

    public static final String CATEGORY_FITNESS = "4";         //科学健身
    public static final String CATEGORY_INVESTMENT = "5";      //投资理财
    public static final String CATEGORY_HEALTH_PRODUCTS = "6"; //保健品
    public static final String CATEGORY_OTHER = "7";           //其他
    public static final String CATEGORY_ALL = "5,6,7";         //全部
    public static final String CATEGORY_EDUCATION = "8";       //教育课程
    public static final String CATEGORY_MEDICAL = "9";         //医疗课程
    public static final String CATEGORY_SCIENCE = "10";        //科普课程

    /**
     * category : 0
     * createTime :
     * duration :
     * id : 0
     * imgUrl :
     * name :
     * sort : 0
     * status : 0
     * updateTime :
     * url :
     */
    private int category;
    private String createTime;
    private String duration;
    private int id;
    private String imgUrl;
    private String name;
    private int sort;
    private int status;
    private String updateTime;
    private String url;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
