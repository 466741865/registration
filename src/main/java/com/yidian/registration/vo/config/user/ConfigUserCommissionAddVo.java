package com.yidian.registration.vo.config.user;


public class ConfigUserCommissionAddVo {

    private Long belongId;

    private Long hospitalId;

    private Long itemId;

    private String commission;

    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long belongId) {
        this.belongId = belongId;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", belongId=").append(belongId);
        sb.append(", hospitalId=").append(hospitalId);
        sb.append(", itemId=").append(itemId);
        sb.append(", commission=").append(commission);
        sb.append("]");
        return sb.toString();
    }
}