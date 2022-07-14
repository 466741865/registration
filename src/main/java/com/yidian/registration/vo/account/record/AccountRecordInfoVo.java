package com.yidian.registration.vo.account.record;

public class AccountRecordInfoVo {

    private String invoiceMoney;

    private String patientName;

    private String invoiceDate;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", invoiceMoney=").append(invoiceMoney);
        sb.append(", invoiceDate=").append(invoiceDate);
        sb.append(", patientName=").append(patientName);
        sb.append("]");
        return sb.toString();
    }
}