package com.yidian.registration.service.impl;

import com.yidian.registration.dao.TConfigHospitalDao;
import com.yidian.registration.entity.TConfigHospital;
import com.yidian.registration.enums.UserStatusEnum;
import com.yidian.registration.service.IConfigHospitalService;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.config.hospital.ConfigHospitalAddVo;
import com.yidian.registration.vo.config.hospital.ConfigHospitalDetailVo;
import com.yidian.registration.vo.config.hospital.ConfigHospitalUpdateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2022/6/11 10:34
 */
@Service
public class ConfigHospitalServiceImpl implements IConfigHospitalService {

    Logger logger = LoggerFactory.getLogger(ConfigHospitalServiceImpl.class);

    @Resource
    private TConfigHospitalDao configHospitalDao;


    @Override
    public PageVo<List<ConfigHospitalDetailVo>> getHospitalConfigList(String name, Integer pageNo, Integer pageSize) {
        logger.info("getHospitalConfigList start, name:{}, pageNo:{}, pageSize:{}", name, pageNo, pageSize);
        PageVo<List<ConfigHospitalDetailVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //查询总量
        int configListTotal = configHospitalDao.selectHospitalListTotal(name);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TConfigHospital> configList = configHospitalDao.selectHospitalConfigList(name, index, pageSize);
        if (CollectionUtils.isEmpty(configList)) {
            return pageVo;
        }
        List<ConfigHospitalDetailVo> list = new ArrayList<>();
        for (TConfigHospital hospital : configList) {
            ConfigHospitalDetailVo vo = entityToVo(hospital);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getHospitalConfigList start, name:{}, pageNo:{}, pageSize:{}, pageVo:{}", name, pageNo, pageSize, pageVo);
        return pageVo;
    }

    /**
     * 实体转换
     *
     * @param hospital
     * @return
     */
    private ConfigHospitalDetailVo entityToVo(TConfigHospital hospital) {
        ConfigHospitalDetailVo vo = new ConfigHospitalDetailVo();
        vo.setId(hospital.getId());
        vo.setBasicSalary(hospital.getBasicSalary());
        vo.setStatus(hospital.getStatus());
        vo.setCreateTime(hospital.getCreateTime());
        return vo;
    }


    @Override
    public ConfigHospitalDetailVo getHospitalConfigDeail(Long id) {
        logger.info("getHospitalConfigDeail start id:{}", id);
        TConfigHospital hospital = configHospitalDao.selectInfoById(id);
        if (Objects.isNull(hospital)) {
            return null;
        }
        ConfigHospitalDetailVo vo = entityToVo(hospital);
        logger.info("getHospitalConfigDeail end id:{}, infoVo:{}", id, vo);
        return vo;
    }

    @Override
    public boolean addHospitalConfig(ConfigHospitalAddVo addVo) {
        logger.info("addHospitalConfig start addVo:{}", addVo);
        TConfigHospital hospital = new TConfigHospital();
        hospital.setHospitalName(addVo.getHospitalName());
        hospital.setBasicSalary(new BigDecimal(addVo.getBasicSalary()));
        hospital.setStatus((byte) UserStatusEnum.ENABLED.getCode());
        int insert = configHospitalDao.insert(hospital);
        logger.info("addHospitalConfig end addVo:{}, insertres:{}", addVo, insert);
        return insert > 0;
    }

    @Override
    public boolean updateHospitalConfig(ConfigHospitalUpdateVo updateVo) {
        TConfigHospital hospital = configHospitalDao.selectInfoById(updateVo.getId());
        hospital.setHospitalName(updateVo.getHospitalName());
        hospital.setBasicSalary(new BigDecimal(updateVo.getBasicSalary()));
        int result = configHospitalDao.updateHospitalConfig(hospital);
        return result > 0;
    }

    @Override
    public boolean deleteConfig(Long id) {
        TConfigHospital hospital = new TConfigHospital();
        hospital.setId(id);
        hospital.setStatus((byte) UserStatusEnum.DISABLE.getCode());
        int result = configHospitalDao.updateHospitalConfig(hospital);
        return result > 0;
    }
}
