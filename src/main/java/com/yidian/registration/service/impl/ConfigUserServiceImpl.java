package com.yidian.registration.service.impl;

import com.yidian.registration.dao.TConfigUserDao;
import com.yidian.registration.entity.TConfigUser;
import com.yidian.registration.enums.UserCommissionTypeEnum;
import com.yidian.registration.enums.UserStatusEnum;
import com.yidian.registration.service.IConfigUserService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.config.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
public class ConfigUserServiceImpl implements IConfigUserService {

    Logger logger = LoggerFactory.getLogger(ConfigUserServiceImpl.class);

    @Resource
    private TConfigUserDao configUserDao;

    @Override
    public PageVo<List<ConfigUserDeatilVo>> getUserConfigList(String name, Integer type, Integer pageNo, Integer pageSize) {
        logger.info("getUserConfigList start, name:{}, pageNo:{}, pageSize:{}", name, pageNo, pageSize);
        PageVo<List<ConfigUserDeatilVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //查询总量
        int configListTotal = configUserDao.selectConfigListTotal(name, type);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TConfigUser> configList = configUserDao.selectConfigList(name, type, index, pageSize);
        if (CollectionUtils.isEmpty(configList)) {
            return pageVo;
        }
        List<ConfigUserDeatilVo> list = new ArrayList<>();
        for (TConfigUser user : configList) {
            ConfigUserDeatilVo vo = entityToVo(user);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getUserConfigList start, name:{}, pageNo:{}, pageSize:{}, pageVo:{}", name, pageNo, pageSize, pageVo);
        return pageVo;
    }

    /**
     * 实体转换
     *
     * @param user
     * @return
     */
    private ConfigUserDeatilVo entityToVo(TConfigUser user) {
        ConfigUserDeatilVo vo = new ConfigUserDeatilVo();
        vo.setId(user.getId());
        vo.setName(user.getName());
        vo.setPhone(user.getPhone());
        vo.setType(user.getType());
        vo.setStatus(user.getStatus());
        vo.setCreateTime(DateBuilder.formatDate(user.getCreateTime(), DateBuilder.FORMAT_FULL));
        vo.setUpdateTime(DateBuilder.formatDate(user.getUpdateTime(), DateBuilder.FORMAT_FULL));
        return vo;
    }


    @Override
    public ConfigUserDeatilVo getUserConfigDeail(Long id) {
        logger.info("getUserConfigDeail start id:{}", id);
        TConfigUser user = configUserDao.selectInfoById(id);
        if (Objects.isNull(user)) {
            return null;
        }
        ConfigUserDeatilVo vo = entityToVo(user);
        logger.info("getUserConfigDeail end id:{}, infoVo:{}", id, vo);
        return vo;
    }

    @Override
    public boolean addUserConfig(ConfigUserAddVo addVo) {
        logger.info("addUserConfig start addVo:{}", addVo);
        TConfigUser user = new TConfigUser();
        user.setName(addVo.getName());
        user.setPhone(addVo.getPhone());
        user.setType(UserCommissionTypeEnum.DEPUTY.getType().byteValue());
        user.setStatus((byte) UserStatusEnum.ENABLED.getCode());
        int insert = configUserDao.insert(user);
        logger.info("addUserConfig end addVo:{}, insertres:{}", addVo, insert);
        return insert > 0;
    }

    @Override
    public boolean updateUserConfig(ConfigUserUpdateVo updateVo) {
        TConfigUser user = configUserDao.selectInfoById(updateVo.getId());
        if (Objects.isNull(user)) {
            return false;
        }
        user.setName(updateVo.getName());
        user.setPhone(updateVo.getPhone());
        user.setStatus(updateVo.getStatus());
        int result = configUserDao.updateConfig(user);
        return result > 0;
    }

    @Override
    public boolean deleteConfig(Long id) {
        TConfigUser user = new TConfigUser();
        user.setId(id);
        user.setStatus((byte) UserStatusEnum.DISABLE.getCode());
        int result = configUserDao.updateConfig(user);
        return result > 0;
    }
}
