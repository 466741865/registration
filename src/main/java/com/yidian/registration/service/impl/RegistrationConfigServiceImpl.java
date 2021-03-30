package com.yidian.registration.service.impl;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.dao.TRegistrationConfigDao;
import com.yidian.registration.entity.TRegistrationConfig;
import com.yidian.registration.enums.UserStatusEnum;
import com.yidian.registration.service.IRegistrationConfigService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.registration.ConfigAddVo;
import com.yidian.registration.vo.registration.ConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/1/21 23:29
 */
@Service
public class RegistrationConfigServiceImpl implements IRegistrationConfigService {
    Logger logger = LoggerFactory.getLogger(RegistrationConfigServiceImpl.class);

    @Autowired
    private TRegistrationConfigDao registrationConfigDao;

    @Override
    public PageVo<List<ConfigVo>> getConfigList(Date date, Integer pageNo, Integer pageSize) {
        logger.info("getConfigList start, date:{}, pageNo:{}, pageSize:{}", JSON.toJSON(date), pageNo, pageSize);
        PageVo<List<ConfigVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        Long day = Long.parseLong(DateBuilder.formatDate(date, DateBuilder.FORMAT_YMD));
        //查询总量
        int configListTotal = registrationConfigDao.getConfigListTotal(day);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TRegistrationConfig> configList = registrationConfigDao.getConfigList(day, index, pageSize);
        if (CollectionUtils.isEmpty(configList)) {
            return pageVo;
        }
        List<ConfigVo> list = new ArrayList<>();
        for (TRegistrationConfig config : configList) {
            ConfigVo vo = entityToVo(config);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getConfigList end, date:{}, pageNo:{}, pageSize:{}, pageVo:{}", JSON.toJSON(date), pageNo, pageSize, pageVo);
        return pageVo;
    }

    @Override
    public Boolean addConfig(ConfigAddVo addVo) {
        logger.info("addConfig start,configVo:{}", JSON.toJSON(addVo));
        TRegistrationConfig config = new TRegistrationConfig();
        config.setDate(Long.parseLong(DateBuilder.formatDate(addVo.getDate(), DateBuilder.FORMAT_YMD)));
        config.setDayType(addVo.getType());
        config.setDuration(addVo.getDuration());
        config.setStartTime(DateBuilder.formatDate(addVo.getStartTime(), DateBuilder.FORMAT_HM));
        config.setNums(addVo.getNums());
        config.setCreator("");
        config.setCreateTime(new Date());
        config.setUpdateTime(new Date());
        config.setState(UserStatusEnum.ENABLED.getCode());

        int res = registrationConfigDao.insert(config);
        if (res > 0) {
            logger.info("addConfig success ,id:{}", config.getId());
            return true;
        }
        return false;
    }

    @Override
    public ConfigVo getConfigById(Long id) {
        logger.info("getConfigById start,id:{}", id);
        TRegistrationConfig config = registrationConfigDao.getConfigById(id);
        if (Objects.isNull(config)) {
            return null;
        }
        ConfigVo vo = entityToVo(config);
        logger.info("getConfigById end,id:{},res:{}", id, vo);
        return vo;
    }

    @Override
    public Boolean deleteConfig(Long id) {
        logger.info("deleteConfig start,id:{}", id);
        boolean result = false;
        TRegistrationConfig config = registrationConfigDao.getConfigById(id);
        if (Objects.isNull(config)) {
            return result;
        }
        int res = registrationConfigDao.deleteById(id);
        if (res > 0) {
            result = true;
        }

        logger.info("deleteConfig end,id:{},result:{}", id, result);
        return result;
    }

    /**
     * entity to vo
     *
     * @param config
     * @return
     */
    private ConfigVo entityToVo(TRegistrationConfig config) {
        ConfigVo vo = new ConfigVo();
        vo.setId(config.getId());
        vo.setType(config.getDayType());
        vo.setDate(DateBuilder.convertStringToDate(String.valueOf(config.getDate()), DateBuilder.YYYY_MM_DD));
        vo.setNums(config.getNums());
        vo.setDuration(config.getDuration());
        vo.setStartTime(DateBuilder.convertStringToDate(config.getStartTime(), DateBuilder.FORMAT_HM));
        vo.setCreator(config.getCreator());
        return vo;
    }
}
