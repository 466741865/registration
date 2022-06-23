package com.yidian.registration.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TAccountUserDivide implements Serializable {
    private Long id;

    private String month;

    private Long belongId;

    private String belongName;

    private BigDecimal invoiceTotalMoney;

    private BigDecimal income;

    private BigDecimal commissionTotalMoney;

    private BigDecimal commissionMoney;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private List<TAccountUserDivideDetail> itemDivides;

    private static final long serialVersionUID = 1L;

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

    public Long getBelongId() {
        return belongId;
    }

    public void setBelongId(Long belongId) {
        this.belongId = belongId;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName == null ? null : belongName.trim();
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

    public BigDecimal getCommissionTotalMoney() {
        return commissionTotalMoney;
    }

    public void setCommissionTotalMoney(BigDecimal commissionTotalMoney) {
        this.commissionTotalMoney = commissionTotalMoney;
    }

    public BigDecimal getCommissionMoney() {
        return commissionMoney;
    }

    public void setCommissionMoney(BigDecimal commissionMoney) {
        this.commissionMoney = commissionMoney;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<TAccountUserDivideDetail> getItemDivides() {
        return itemDivides;
    }

    public void setItemDivides(List<TAccountUserDivideDetail> itemDivides) {
        this.itemDivides = itemDivides;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", month=").append(month);
        sb.append(", belongId=").append(belongId);
        sb.append(", belongName=").append(belongName);
        sb.append(", invoiceTotalMoney=").append(invoiceTotalMoney);
        sb.append(", income=").append(income);
        sb.append(", commissionTotalMoney=").append(commissionTotalMoney);
        sb.append(", commissionMoney=").append(commissionMoney);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}