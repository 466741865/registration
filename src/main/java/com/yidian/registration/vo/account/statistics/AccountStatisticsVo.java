package com.yidian.registration.vo.account.statistics;

import java.math.BigDecimal;
import java.util.List;

public class AccountStatisticsVo {

    private Long id;

    private String month;

    private Long hospitalId;

    private String hospitalName;

    private BigDecimal invoiceTotalMoney;

    private BigDecimal income;

    private BigDecimal basicSalary;

    private Byte status;

    private String createTime;

    private String updateTime;

    private List<AccountStatisticsDetailVo> detailList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public BigDecimal getInvoiceTotalMoney() {
        return invoiceTotalMoney;
    }

    public void setInvoiceTotalMoney(BigDecimal invoiceTotalMoney) {
        this.invoiceTotalMoney = invoiceTotalMoney;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<AccountStatisticsDetailVo> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<AccountStatisticsDetailVo> detailList) {
        this.detailList = detailList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", id=").append(id);
        sb.append(", month=").append(month);
        sb.append(", hospitalId=").append(hospitalId);
        sb.append(", hospitalName=").append(hospitalName);
        sb.append(", invoiceTotalMoney=").append(invoiceTotalMoney);
        sb.append(", income=").append(income);
        sb.append(", basicSalary=").append(basicSalary);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", detailList=").append(detailList);
        sb.append("]");
        return sb.toString();
    }
}