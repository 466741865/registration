package com.yidian.registration.vo.account.statistics;

public class AccountStatisticsBuildVo {

    private Long hospitalId;

    private String settleDate;

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate == null ? null : settleDate.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", hospitalId=").append(hospitalId);
        sb.append(", settleDate=").append(settleDate);
        sb.append("]");
        return sb.toString();
    }
}