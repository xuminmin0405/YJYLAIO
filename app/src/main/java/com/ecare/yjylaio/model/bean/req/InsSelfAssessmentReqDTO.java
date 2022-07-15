package com.ecare.yjylaio.model.bean.req;

/**
 * NeighborhoodLongevity
 * <p>
 * Created by xuminmin on 12/13/21.
 * Email: iminminxu@gmail.com
 */
public class InsSelfAssessmentReqDTO {

    private String address;
    private String city;
    private Integer confirmApply;
    private String contactIdcard;
    private String contactName;
    private String contactPhone;
    private Integer custContactId;
    private String district;
    private String insIdcard;
    private String insName;
    private String province;
    private Integer q1;
    private Integer q2;
    private Integer q3;
    private Integer q4;
    private Integer q5;
    private Integer q6;
    private Integer relationshipInsUser;
    private String street;

    public InsSelfAssessmentReqDTO(String insIdcard, Integer q1, Integer q2, Integer q3, Integer q4, Integer q5, Integer q6) {
        this.insIdcard = insIdcard;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getConfirmApply() {
        return confirmApply;
    }

    public void setConfirmApply(Integer confirmApply) {
        this.confirmApply = confirmApply;
    }

    public String getContactIdcard() {
        return contactIdcard;
    }

    public void setContactIdcard(String contactIdcard) {
        this.contactIdcard = contactIdcard;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getCustContactId() {
        return custContactId;
    }

    public void setCustContactId(Integer custContactId) {
        this.custContactId = custContactId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getInsIdcard() {
        return insIdcard;
    }

    public void setInsIdcard(String insIdcard) {
        this.insIdcard = insIdcard;
    }

    public String getInsName() {
        return insName;
    }

    public void setInsName(String insName) {
        this.insName = insName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getQ1() {
        return q1;
    }

    public void setQ1(Integer q1) {
        this.q1 = q1;
    }

    public Integer getQ2() {
        return q2;
    }

    public void setQ2(Integer q2) {
        this.q2 = q2;
    }

    public Integer getQ3() {
        return q3;
    }

    public void setQ3(Integer q3) {
        this.q3 = q3;
    }

    public Integer getQ4() {
        return q4;
    }

    public void setQ4(Integer q4) {
        this.q4 = q4;
    }

    public Integer getQ5() {
        return q5;
    }

    public void setQ5(Integer q5) {
        this.q5 = q5;
    }

    public Integer getQ6() {
        return q6;
    }

    public void setQ6(Integer q6) {
        this.q6 = q6;
    }

    public Integer getRelationshipInsUser() {
        return relationshipInsUser;
    }

    public void setRelationshipInsUser(Integer relationshipInsUser) {
        this.relationshipInsUser = relationshipInsUser;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
