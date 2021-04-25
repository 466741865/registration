package com.yidian.registration.vo.cure;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:32
 */
public class PatientCureInfoVo {

    private Long id;

    /**
     * 病号id
     */
    private Long pid;

    /**
     *
     */
    private String patientName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 治疗日期
     */
    private String cureDate;

    /**
     * 治疗时间
     */
    private String cureTime;

    /**
     * 项目
     */
    private String items;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public String getCureDate() {
        return cureDate;
    }

    public void setCureDate(String cureDate) {
        this.cureDate = cureDate;
    }

    public String getCureTime() {
        return cureTime;
    }

    public void setCureTime(String cureTime) {
        this.cureTime = cureTime;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PatientCureInfoVo{" +
                "id=" + id +
                "pid=" + pid +
                ", patientName='" + patientName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", cureDate='" + cureDate + '\'' +
                ", cureTime='" + cureTime + '\'' +
                ", items='" + items + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
