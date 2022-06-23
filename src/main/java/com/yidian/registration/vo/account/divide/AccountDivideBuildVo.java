package com.yidian.registration.vo.account.divide;

public class AccountDivideBuildVo {

    private Long belongId;

    private String settleDate;

    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long belongId) {
        this.belongId = belongId;
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
        sb.append(", belongId=").append(belongId);
        sb.append(", settleDate=").append(settleDate);
        sb.append("]");
        return sb.toString();
    }
}