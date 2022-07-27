package com.yidian.registration.vo.account.record;

import java.util.List;

public class AccountRecordBatchAddVo {

    private String settleDate;

    private Long hospitalId;

    private Long itemId;

    private Long belongId;

    /**
     * 记录信息
     */
    private List<AccountRecordInfoVo> recordInfoList;


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

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate == null ? null : settleDate.trim();
    }

    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long belongId) {
        this.belongId = belongId;
    }

    public List<AccountRecordInfoVo> getRecordInfoList() {
        return recordInfoList;
    }

    public void setRecordInfoList(List<AccountRecordInfoVo> recordInfoList) {
        this.recordInfoList = recordInfoList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" [");
        sb.append(", hospitalId=").append(hospitalId);
        sb.append(", itemId=").append(itemId);
        sb.append(", settleDate=").append(settleDate);
        sb.append(", belongId=").append(belongId);
        sb.append(", recordInfoList=").append(recordInfoList);
        sb.append("]");
        return sb.toString();
    }
}