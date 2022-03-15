package com.ecare.yjylaio.model.bean.rsp;

/**
 * ProjectName: YJYLAIO
 * Package: com.ecare.yjylaio.model.bean.rsp
 * ClassName: UserHealthDataRspDTO
 * Description:
 * Author:
 * CreateDate: 2021/6/24 14:03
 * Version: 1.0
 */
public class UserHealthDataRspDTO {

    /**
     * bloodPressure :
     * bloodSugar :
     * heartRate :
     * stepss :
     * temperature :
     */
    private String bloodPressure;
    private String bloodSugar;
    private String heartRate;
    private String stepss;
    private String temperature;
    private String height;
    private String weight;

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getStepss() {
        return stepss;
    }

    public void setStepss(String stepss) {
        this.stepss = stepss;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
