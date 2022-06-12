package com.yidian.registration.vo.config.hospital;


public class ConfigHospitalUpdateVo {

    private Long id;

    private String hospitalName;

    private String basicSalary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        sb.append("id = ").append(id);
        sb.append(", hospitalName=").append(hospitalName);
        sb.append(", basicSalary=").append(basicSalary);
        sb.append("]");
        return sb.toString();
    }
}