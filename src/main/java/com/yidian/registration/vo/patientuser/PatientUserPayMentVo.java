package com.yidian.registration.vo.patientuser;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:32
 */
public class PatientUserPayMentVo {

    /**
     * 用户id
     */
    private Long pid;

    /**
     * 购买次数
     */
    private int buyNum;

    /**
     *  支付金额，单位元
     */
    private String payMoney;

    /**
     * 购买日期
     */
    private String payDate;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
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

    @Override
    public String toString() {
        return "PatientUserPayMentVo{" +
                "pid=" + pid +
                ", buyNum=" + buyNum +
                ", payMoney='" + payMoney + '\'' +
                ", payDate='" + payDate + '\'' +
                '}';
    }
}
