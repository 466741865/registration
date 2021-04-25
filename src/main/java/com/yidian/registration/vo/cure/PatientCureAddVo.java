package com.yidian.registration.vo.cure;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:32
 */
public class PatientCureAddVo {

    /**
     * 病号id
     */
    private Long pid;

    /**
     * 治疗日期
     */
    private String cureDate;

    private String cureTime;

    private String items;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    @Override
    public String toString() {
        return "PatientCureInfoVo{" +
                "pid=" + pid +
                ", cureDate='" + cureDate + '\'' +
                ", cureTime='" + cureTime + '\'' +
                ", items='" + items + '\'' +
                '}';
    }
}
