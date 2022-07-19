package com.yidian.registration.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TAccountStatisticsDayDetail implements Serializable {
    private Long id;

    private Long statisticsDayId;

    private Long statisticsId;

    private String month;

    private String day;

    private Long hospitalId;

    private Long itemId;

    private String itemName;

    private BigDecimal itemTotalMoney;

    private BigDecimal commission;

    private BigDecimal itemIncome;

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

    public Long getStatisticsDayId() {
        return statisticsDayId;
    }

    public void setStatisticsDayId(Long statisticsDayId) {
        this.statisticsDayId = statisticsDayId;
    }

    public Long getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(Long statisticsId) {
        this.statisticsId = statisticsId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public BigDecimal getItemTotalMoney() {
        return itemTotalMoney;
    }

    public void setItemTotalMoney(BigDecimal itemTotalMoney) {
        this.itemTotalMoney = itemTotalMoney;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getItemIncome() {
        return itemIncome;
    }

    public void setItemIncome(BigDecimal itemIncome) {
        this.itemIncome = itemIncome;
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
        sb.append(", statisticsDayId=").append(statisticsDayId);
        sb.append(", statisticsId=").append(statisticsId);
        sb.append(", month=").append(month);
        sb.append(", day=").append(day);
        sb.append(", hospitalId=").append(hospitalId);
        sb.append(", itemId=").append(itemId);
        sb.append(", itemName=").append(itemName);
        sb.append(", itemTotalMoney=").append(itemTotalMoney);
        sb.append(", commission=").append(commission);
        sb.append(", itemIncome=").append(itemIncome);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}