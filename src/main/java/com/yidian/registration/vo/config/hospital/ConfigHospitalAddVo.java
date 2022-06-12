package com.yidian.registration.vo.config.hospital;


public class ConfigHospitalAddVo {

    private String hospitalName;

    private String basicSalary;

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    public String getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(String basicSalary) {
        this.basicSalary = basicSalary;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", hospitalName=").append(hospitalName);
        sb.append(", basicSalary=").append(basicSalary);
        sb.append("]");
        return sb.toString();
    }
}