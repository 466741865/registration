package com.yidian.registration.vo.account.statistics;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TAccountStatisticsDayVO implements Serializable {
    private Long id;

    private Long statisticsId;

    private String month;

    private String day;

    private Long hospitalId;

    private String hospitalName;

    private BigDecimal totalInvoiceMoney;

    private BigDecimal totalIncome;

    private List<TAccountStatisticsDayDetailVO> dayDetailList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public BigDecimal getTotalInvoiceMoney() {
        return totalInvoiceMoney;
    }

    public void setTotalInvoiceMoney(BigDecimal totalInvoiceMoney) {
        this.totalInvoiceMoney = totalInvoiceMoney;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public List<TAccountStatisticsDayDetailVO> getDayDetailList() {
        return dayDetailList;
    }

    public void setDayDetailList(List<TAccountStatisticsDayDetailVO> dayDetailList) {
        this.dayDetailList = dayDetailList;
    }

    @Override
    public String toString() {
        return "TAccountStatisticsDayVO{" +
                "id=" + id +
                ", statisticsId=" + statisticsId +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", hospitalId=" + hospitalId +
                ", hospitalName='" + hospitalName + '\'' +
                ", totalInvoiceMoney=" + totalInvoiceMoney +
                ", totalIncome=" + totalIncome +
                ", dayDetailList=" + dayDetailList +
                '}';
    }
}