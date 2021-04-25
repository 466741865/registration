package com.yidian.registration.vo.patientuser;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:32
 */
public class PatientUserInfoVo {

    /**
     * userid
     */
    private Long id;

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
    private String sex;

    /**
     * 区域
     */
    private String area;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 账号状态
     */
    private int status;

    /**
     * 剩余治疗次数
     */
    private int remainNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(int remainNum) {
        this.remainNum = remainNum;
    }

    @Override
    public String toString() {
        return "PatientUserListVo{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex='" + sex + '\'' +
                ", area='" + area + '\'' +
                ", creator='" + creator + '\'' +
                ", status=" + status +
                ", remainNum=" + remainNum +
                '}';
    }
}
