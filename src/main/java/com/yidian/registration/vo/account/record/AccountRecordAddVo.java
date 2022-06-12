package com.yidian.registration.vo.account.record;

public class AccountRecordAddVo {

    private String patientName;

    private Long hospitalId;

    private Long itemId;

    private String invoiceMoney;

    private String invoiceDate;

    private String settleDate;

    private Long belongId;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
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

    public String getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(String invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate == null ? null : invoiceDate.trim();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", patientName=").append(patientName);
        sb.append(", hospitalId=").append(hospitalId);
        sb.append(", itemId=").append(itemId);
        sb.append(", invoiceMoney=").append(invoiceMoney);
        sb.append(", invoiceDate=").append(invoiceDate);
        sb.append(", settleDate=").append(settleDate);
        sb.append(", belongId=").append(belongId);
        sb.append("]");
        return sb.toString();
    }
}