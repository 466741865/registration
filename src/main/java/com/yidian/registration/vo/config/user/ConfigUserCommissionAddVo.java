package com.yidian.registration.vo.config.user;


public class ConfigUserCommissionAddVo {

    private String name;

    private String phone;

    private Long itemId;

    private String commission;

    private Byte type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", name=").append(name);
        sb.append(", phone=").append(phone);
        sb.append(", itemId=").append(itemId);
        sb.append(", commission=").append(commission);
        sb.append(", type=").append(type);
        sb.append("]");
        return sb.toString();
    }
}