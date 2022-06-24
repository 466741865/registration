package com.yidian.registration.vo.account.divide;


import java.math.BigDecimal;
import java.util.List;

public class AccountDivideVo {
    private Long id;

    private String month;

    private Long belongId;

    private Integer userType;

    private String belongName;

    private BigDecimal invoiceTotalMoney;

    private BigDecimal income;

    private BigDecimal commissionTotalMoney;

    private BigDecimal commissionMoney;

    private Byte status;

    private String createTime;

    private String updateTime;

    private List<AccountDivideDetailVo> itemDivides;

    private List<AccountDivideDetailVo> itemCommissions;

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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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

    public List<AccountDivideDetailVo> getItemDivides() {
        return itemDivides;
    }

    public void setItemDivides(List<AccountDivideDetailVo> itemDivides) {
        this.itemDivides = itemDivides;
    }

    public List<AccountDivideDetailVo> getItemCommissions() {
        return itemCommissions;
    }

    public void setItemCommissions(List<AccountDivideDetailVo> itemCommissions) {
        this.itemCommissions = itemCommissions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", id=").append(id);
        sb.append(", month=").append(month);
        sb.append(", belongId=").append(belongId);
        sb.append(", belongName=").append(belongName);
        sb.append(", userType=").append(userType);
        sb.append(", invoiceTotalMoney=").append(invoiceTotalMoney);
        sb.append(", income=").append(income);
        sb.append(", commissionTotalMoney=").append(commissionTotalMoney);
        sb.append(", commissionMoney=").append(commissionMoney);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", itemDivides=").append(itemDivides);
        sb.append(", itemCommissions=").append(itemCommissions);
        sb.append("]");
        return sb.toString();
    }
}