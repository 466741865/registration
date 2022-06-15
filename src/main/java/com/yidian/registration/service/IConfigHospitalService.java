package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.config.hospital.ConfigHospitalAddVo;
import com.yidian.registration.vo.config.hospital.ConfigHospitalDetailVo;
import com.yidian.registration.vo.config.hospital.ConfigHospitalUpdateVo;

import java.util.List;

/**
 * @Author: QingHang
 * @Description: 医院配置
 * @Date: 2022/6/11 10:31
 */
public interface IConfigHospitalService {

    /**
     * 查询配置列表
     * @param hospitalName
     * @return
     */
    PageVo<List<ConfigHospitalDetailVo>> getHospitalConfigList(String hospitalName, Integer pageNo, Integer pageSize);

    /**
     * 查询医院详情
     * @param id
     * @return
     */
    ConfigHospitalDetailVo getHospitalConfigDeail(Long id);

    /**
     * 添加医院
     * @param addVo
     * @return
     */
    boolean addHospitalConfig(ConfigHospitalAddVo addVo);

    /**
     * 更新
     * @param updateVo
     * @return
     */
    boolean updateHospitalConfig(ConfigHospitalUpdateVo updateVo);

    /**
     * 删除配置
     * @param id
     * @return
     */
    boolean deleteConfig(Long id);

}
