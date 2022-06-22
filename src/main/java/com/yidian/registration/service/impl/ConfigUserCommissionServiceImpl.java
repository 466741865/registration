package com.yidian.registration.service.impl;

import com.yidian.registration.dao.TConfigHospitalDao;
import com.yidian.registration.dao.TConfigItemDao;
import com.yidian.registration.dao.TConfigUserCommissionDao;
import com.yidian.registration.dao.TConfigUserDao;
import com.yidian.registration.entity.TConfigHospital;
import com.yidian.registration.entity.TConfigItem;
import com.yidian.registration.entity.TConfigUser;
import com.yidian.registration.entity.TConfigUserCommission;
import com.yidian.registration.enums.UserCommissionTypeEnum;
import com.yidian.registration.enums.UserStatusEnum;
import com.yidian.registration.service.IConfigUserCommissionService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.config.item.ConfigItemDetailVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionAddVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionDeatilVo;
import com.yidian.registration.vo.config.user.ConfigUserCommissionUpdateVo;
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
public class ConfigUserCommissionServiceImpl implements IConfigUserCommissionService {

    Logger logger = LoggerFactory.getLogger(ConfigUserCommissionServiceImpl.class);

    @Resource
    private TConfigUserCommissionDao configUserCommissionDao;

    @Resource
    private TConfigUserDao configUserDao;

    @Resource
    private TConfigItemDao configItemDao;

    @Resource
    private TConfigHospitalDao configHospitalDao;


    @Override
    public PageVo<List<ConfigUserCommissionDeatilVo>> getUserCommissionConfigList(Long belongId, Integer pageNo, Integer pageSize) {
        logger.info("getUserCommissionConfigList start, belongId:{}, pageNo:{}, pageSize:{}", belongId, pageNo, pageSize);
        PageVo<List<ConfigUserCommissionDeatilVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //查询总量
        int configListTotal = configUserCommissionDao.selectConfigListTotal(belongId);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TConfigUserCommission> configList = configUserCommissionDao.selectConfigList(belongId, index, pageSize);
        if (CollectionUtils.isEmpty(configList)) {
            return pageVo;
        }
        List<ConfigUserCommissionDeatilVo> list = new ArrayList<>();
        for (TConfigUserCommission commission : configList) {
            ConfigUserCommissionDeatilVo vo = setUserCommissionVo(commission);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getUserCommissionConfigList start, belongId:{}, pageNo:{}, pageSize:{}, pageVo:{}", belongId, pageNo, pageSize, pageVo);
        return pageVo;
    }

    private ConfigUserCommissionDeatilVo setUserCommissionVo(TConfigUserCommission commission) {
        ConfigUserCommissionDeatilVo vo = entityToVo(commission);
        //查询user信息
        TConfigUser user = configUserDao.selectInfoById(commission.getBelongId());
        if (Objects.nonNull(user)) {
            vo.setBelongName(user.getName());
        }

        //查询项目信息
        TConfigItem item = configItemDao.selectInfoById(commission.getItemId());
        if (Objects.nonNull(item)) {
            vo.setItemName(item.getItemName());
            //查询单位信息
            TConfigHospital hospital = configHospitalDao.selectInfoById(item.getHospitalId());
            if (Objects.nonNull(hospital)) {
                vo.setHospitalName(hospital.getHospitalName());
            }
        }
        return vo;
    }

    /**
     * 实体转换
     *
     * @param userCommission
     * @return
     */
    private ConfigUserCommissionDeatilVo entityToVo(TConfigUserCommission userCommission) {
        ConfigUserCommissionDeatilVo vo = new ConfigUserCommissionDeatilVo();
        vo.setId(userCommission.getId());
        vo.setBelongId(userCommission.getBelongId());
        vo.setHospitalId(userCommission.getHospitalId());
        vo.setItemId(userCommission.getItemId());
        vo.setCommission(userCommission.getCommission().toString());
        vo.setStatus(userCommission.getStatus());
        vo.setCreateTime(DateBuilder.formatDate(userCommission.getCreateTime(), DateBuilder.FORMAT_FULL));
        vo.setUpdateTime(DateBuilder.formatDate(userCommission.getUpdateTime(), DateBuilder.FORMAT_FULL));
        return vo;
    }


    @Override
    public ConfigUserCommissionDeatilVo getUserCommissionConfigDeail(Long id) {
        logger.info("getUserCommissionConfigDeail start id:{}", id);
        TConfigUserCommission commission = configUserCommissionDao.selectInfoById(id);
        if (Objects.isNull(commission)) {
            return null;
        }
        ConfigUserCommissionDeatilVo vo = setUserCommissionVo(commission);
        logger.info("getUserCommissionConfigDeail end id:{}, infoVo:{}", id, vo);
        return vo;
    }

    @Override
    public boolean addUserCommissionConfig(ConfigUserCommissionAddVo addVo) {
        logger.info("addUserCommissionConfig start addVo:{}", addVo);
        TConfigUserCommission userCommission = new TConfigUserCommission();
        userCommission.setBelongId(addVo.getBelongId());
        userCommission.setHospitalId(addVo.getHospitalId());
        userCommission.setItemId(addVo.getItemId());
        userCommission.setCommission(new BigDecimal(addVo.getCommission()));
        userCommission.setStatus((byte) UserStatusEnum.ENABLED.getCode());
        int insert = configUserCommissionDao.insert(userCommission);
        logger.info("addUserCommissionConfig end addVo:{}, insertres:{}", addVo, insert);
        return insert > 0;
    }

    @Override
    public boolean updateUserCommissionConfig(ConfigUserCommissionUpdateVo updateVo) {
        TConfigUserCommission userCommission = configUserCommissionDao.selectInfoById(updateVo.getId());
        if (Objects.isNull(userCommission)) {
            return false;
        }
        userCommission.setBelongId(updateVo.getBelongId());
        userCommission.setHospitalId(updateVo.getHospitalId());
        userCommission.setItemId(updateVo.getItemId());
        userCommission.setCommission(new BigDecimal(updateVo.getCommission()));
        userCommission.setStatus(updateVo.getStatus());
        int result = configUserCommissionDao.updateConfig(userCommission);
        return result > 0;
    }

    @Override
    public boolean deleteConfig(Long id) {
        TConfigUserCommission UserCommission = new TConfigUserCommission();
        UserCommission.setId(id);
        UserCommission.setStatus((byte) UserStatusEnum.DISABLE.getCode());
        int result = configUserCommissionDao.updateConfig(UserCommission);
        return result > 0;
    }

    @Override
    public List<ConfigUserCommissionDeatilVo> getUserCommissionConfigListByIid(Long itemId) {
        logger.info("getUserCommissionConfigListByIid start itemId:{}", itemId);
        List<ConfigUserCommissionDeatilVo> list = new ArrayList<>();
        //查询主提成人员
        List<TConfigUser> users = configUserDao.selectListByType(UserCommissionTypeEnum.MAIN.getType());
        if (!CollectionUtils.isEmpty(users)) {
            for (TConfigUser user : users) {
                ConfigUserCommissionDeatilVo detailVo = new ConfigUserCommissionDeatilVo();
                detailVo.setBelongId(user.getId());
                detailVo.setBelongName(user.getName());
                list.add(detailVo);
            }
        }
        //查询副提成人员
        List<TConfigUserCommission> commissions2 = configUserCommissionDao.selectConfigListByIid(itemId);
        if (CollectionUtils.isEmpty(commissions2)) {
            return list;
        }
        for (TConfigUserCommission commission : commissions2) {
            ConfigUserCommissionDeatilVo detailVo = entityToVo(commission);
            //查询user信息
            TConfigUser user = configUserDao.selectInfoById(commission.getBelongId());
            if (Objects.nonNull(user)) {
                detailVo.setBelongName(user.getName());
            }
            list.add(detailVo);
        }
        logger.info("getUserCommissionConfigListByIid end itemId:{}", itemId);
        return list;
    }

    @Override
    public List<ConfigUserCommissionDeatilVo> getUserCommissionConfig(Long belongId, Long hospitalId, Long itemId) {
        if(Objects.isNull(belongId) || Objects.isNull(hospitalId) || Objects.isNull(itemId)){
            return Collections.emptyList();
        }
        List<TConfigUserCommission> userCommissions = configUserCommissionDao.selectCommissionConfigList(belongId, hospitalId, itemId);
        List<ConfigUserCommissionDeatilVo> list = new ArrayList<>();
        for (TConfigUserCommission commission : userCommissions) {
            ConfigUserCommissionDeatilVo detailVo = entityToVo(commission);

            list.add(detailVo);
        }
        return list;
    }
}
