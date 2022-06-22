package com.yidian.registration.vo.config.user;


public class ConfigUserCommissionUpdateVo {
    private Long id;

    private Long belongId;

    private Long hospitalId;

    private Long itemId;

    private String commission;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", id=").append(id);
        sb.append(", belongId=").append(belongId);
        sb.append(", hospitalId=").append(hospitalId);
        sb.append(", itemId=").append(itemId);
        sb.append(", commission=").append(commission);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}