package com.yidian.registration.service.impl;

import com.yidian.registration.dao.TRegistrationDao;
import com.yidian.registration.entity.TRegistration;
import com.yidian.registration.enums.RegistrationOperationEnum;
import com.yidian.registration.enums.RegistrationStateEnum;
import com.yidian.registration.service.IRegistrationService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.registration.RegistrationVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2021/3/7 21:47
 */
@Service
public class RegistrationServiceImpl implements IRegistrationService {

    Logger logger = LoggerFactory.getLogger(RegistrationConfigServiceImpl.class);

    @Autowired
    private TRegistrationDao registrationDao;


    @Override
    public PageVo<List<RegistrationVo>> getRegistrationList(String name, String mobile, Date day, Integer pageNo, Integer pageSize) {
        logger.info("getRegistrationList start, name:{}, mobile:{}, day:{}, pageNo:{}, pageSize:{}", name, mobile, day, pageNo, pageSize);
        PageVo<List<RegistrationVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        Long d = Long.parseLong(DateBuilder.formatDate(day, DateBuilder.FORMAT_YMD));
        //查询总量
        int configListTotal = registrationDao.getRegistrationListTotal(name, mobile, d);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TRegistration> rList = registrationDao.getRegistrationList(name, mobile, d, index, pageSize);
        if (CollectionUtils.isEmpty(rList)) {
            return pageVo;
        }
        List<RegistrationVo> list = new ArrayList<>();
        for (TRegistration registration : rList) {
            RegistrationVo vo = entityToVo(registration);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getRegistrationList end,name:{}, mobile:{}, day:{}, pageNo:{}, pageSize:{}, res:{}", name, mobile, day, pageNo, pageSize, pageVo);
        return pageVo;
    }

    @Override
    public Boolean operation(Long id, Integer operationType) {
        logger.info("cancel 取消预约,start，id:{}", id);
        boolean result = false;

        //根据操作类型获取状态
        RegistrationOperationEnum operationEnum = RegistrationOperationEnum.getOperationEnum(operationType);
        if(null == operationEnum){
            return result;
        }
        RegistrationStateEnum stateEnum = RegistrationStateEnum.Registration;
        switch (operationEnum){
            case BLOCK:
                break;
            case CANCEL:
                break;
            case ABSENT:
                break;
            case SIGN_IN:
                break;
            default:
                break;
        }

        int res = registrationDao.updateRegistrationStateById(id, stateEnum.getCode());
        if (res > 0) {
            result = true;
        }
        logger.info("cancel 取消预约,end，id:{}, result:{}", id, result);
        return result;
    }

    /**
     * entity to vo
     *
     * @param registration
     * @return
     */
    private RegistrationVo entityToVo(TRegistration registration) {
        RegistrationVo vo = new RegistrationVo();
        vo.setId(registration.getId());
        vo.setConfigId(registration.getConfigId());
        vo.setName(registration.getName());
        vo.setPhone(registration.getPhone());
        vo.setState(registration.getState());
        vo.setTherapyDate(DateBuilder.convertStringToDate(String.valueOf(registration.getTherapyDate()), DateBuilder.YYYY_MM_DD));
        vo.setCreator(registration.getCreator());
        vo.setCreateTime(registration.getCreateTime());
        return vo;
    }

}
