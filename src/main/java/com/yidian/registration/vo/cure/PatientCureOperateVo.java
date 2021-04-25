package com.yidian.registration.vo.cure;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:32
 */
public class PatientCureOperateVo {

    /**
     * 病号id
     */
    private Long pid;

    /**
     * 治疗日期
     */
    private Long cureId;

    /**
     * 治疗项目，
     */
    private String items;

    /**
     * 使用次数
     */
    private Integer useNum;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getCureId() {
        return cureId;
    }

    public void setCureId(Long cureId) {
        this.cureId = cureId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    @Override
    public String toString() {
        return "PatientCureOperateVo{" +
                "pid=" + pid +
                ", cureId=" + cureId +
                ", useNum=" + useNum +
                ", items='" + items + '\'' +
                '}';
    }
}
