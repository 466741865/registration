package com.yidian.registration.vo.config.item;



public class ConfigItemAddVo {

    private Long hospitalId;

    private String itemName;

    private String commission;

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
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
        sb.append(", hospitalId=").append(hospitalId);
        sb.append(", itemName=").append(itemName);
        sb.append(", commission=").append(commission);
        sb.append("]");
        return sb.toString();
    }
}