package com.yidian.registration.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TAccountUserDivideDetail implements Serializable {
    private Long id;

    private Long divideId;

    private Long belongId;

    private String belongName;

    private Long hospitalId;

    private String hospitalName;

    private Long itemId;

    private String itemName;

    private BigDecimal invoiceTotalMoney;

    private BigDecimal commission;

    private Byte commissionType;

    private BigDecimal income;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDivideId() {
        return divideId;
    }

    public void setDivideId(Long divideId) {
        this.divideId = divideId;
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
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public BigDecimal getInvoiceTotalMoney() {
        return invoiceTotalMoney;
    }

    public void setInvoiceTotalMoney(BigDecimal invoiceTotalMoney) {
        this.invoiceTotalMoney = invoiceTotalMoney;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Byte getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(Byte commissionType) {
        this.commissionType = commissionType;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", divideId=").append(divideId);
        sb.append(", belongId=").append(belongId);
        sb.append(", belongName=").append(belongName);
        sb.append(", hospitalId=").append(hospitalId);
        sb.append(", hospitalName=").append(hospitalName);
        sb.append(", itemId=").append(itemId);
        sb.append(", itemName=").append(itemName);
        sb.append(", invoiceTotalMoney=").append(invoiceTotalMoney);
        sb.append(", commission=").append(commission);
        sb.append(", commissionType=").append(commissionType);
        sb.append(", income=").append(income);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}