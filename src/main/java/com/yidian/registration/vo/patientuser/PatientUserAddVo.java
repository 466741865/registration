package com.yidian.registration.vo.patientuser;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:32
 */
public class PatientUserAddVo {

    /**
     * 病号名称
     */
    private String patientName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     *  性别，1：男，2：女
     */
    private int sex;

    /**
     * 区域
     */
    private String area;

    /**
     * 创建人
     */
    private String creator;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "PatientUserAddVo{" +
                "patientName='" + patientName + '\'' +
                "mobile='" + mobile + '\'' +
                ", sex=" + sex +
                ", area='" + area + '\'' +
                ", creator='" + creator + '\'' +
                '}';
    }
}
