package com.yidian.registration.vo.usermanager;

/**
 * @author : QingHang
 * @version 1.0
 * @Description : 用户信息info
 * @date : 2019/12/26 17:58
 **/
public class UserInfoVo {


    private Long uid;

    private String userName;

    private String phone;

    private String realName;

    private int state;

    private String email;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserInfoVo{" +
                "uid=" + uid +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", realName='" + realName + '\'' +
                ", state='" + state +
                ", sex='" + email + '\'' +
                '}';
    }
}
