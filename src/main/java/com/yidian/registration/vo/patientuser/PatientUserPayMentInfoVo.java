package com.yidian.registration.vo.patientuser;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:32
 */
public class PatientUserPayMentInfoVo {

    /**
     * 用户id
     */
    private Long pid;

    /**
     * 购买次数
     */
    private Integer buyNum;

    /**
     *  支付金额，单位元
     */
    private String payMoney;

    /**
     * 购买日期
     */
    private String payDate;

    /**
     * 剩余次数
     */
    private Integer remainNum;


    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    @Override
    public String toString() {
        return "PatientUserPayMentInfoVo{" +
                "pid=" + pid +
                ", buyNum=" + buyNum +
                ", payMoney='" + payMoney + '\'' +
                ", payDate='" + payDate + '\'' +
                ", remainNum=" + remainNum +
                '}';
    }
}
