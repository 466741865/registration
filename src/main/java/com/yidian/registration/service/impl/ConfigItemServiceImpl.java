package com.yidian.registration.service.impl;

import com.yidian.registration.dao.TConfigHospitalDao;
import com.yidian.registration.dao.TConfigItemDao;
import com.yidian.registration.entity.TConfigHospital;
import com.yidian.registration.entity.TConfigItem;
import com.yidian.registration.enums.UserStatusEnum;
import com.yidian.registration.service.IConfigItemService;
import com.yidian.registration.utils.DateBuilder;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.config.item.ConfigItemAddVo;
import com.yidian.registration.vo.config.item.ConfigItemDetailVo;
import com.yidian.registration.vo.config.item.ConfigItemUpdateVo;
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
public class ConfigItemServiceImpl implements IConfigItemService {

    Logger logger = LoggerFactory.getLogger(ConfigItemServiceImpl.class);

    @Resource
    private TConfigItemDao configItemDao;

    @Resource
    private TConfigHospitalDao configHospitalDao;


    @Override
    public PageVo<List<ConfigItemDetailVo>> getItemConfigList(String itemName, Integer pageNo, Integer pageSize) {
        logger.info("getItemConfigList start, itemName:{}, pageNo:{}, pageSize:{}", itemName, pageNo, pageSize);
        PageVo<List<ConfigItemDetailVo>> pageVo = new PageVo<>();
        pageVo.setCount(0);
        pageVo.setData(Collections.emptyList());
        pageVo.setPageNum(pageNo);
        pageVo.setPageSize(pageSize);
        //查询总量
        int configListTotal = configItemDao.selectItemListTotal(itemName);
        if (configListTotal <= 0) {
            return pageVo;
        }
        pageVo.setCount(configListTotal);

        int index = (pageNo - 1) * pageSize;
        List<TConfigItem> configList = configItemDao.selectItemConfigList(itemName, index, pageSize);
        if (CollectionUtils.isEmpty(configList)) {
            return pageVo;
        }
        List<ConfigItemDetailVo> list = new ArrayList<>();
        for (TConfigItem item : configList) {
            ConfigItemDetailVo vo = setConfigItemDetailVo(item);
            list.add(vo);
        }
        pageVo.setData(list);
        logger.info("getItemConfigList start, itemName:{}, pageNo:{}, pageSize:{}, pageVo:{}", itemName, pageNo, pageSize, pageVo);
        return pageVo;
    }

    private ConfigItemDetailVo setConfigItemDetailVo(TConfigItem item){
        ConfigItemDetailVo vo = entityToVo(item);
        //查询单位信息
        TConfigHospital hospital = configHospitalDao.selectInfoById(item.getHospitalId());
        if(Objects.nonNull(hospital)){
            vo.setHospitalName(hospital.getHospitalName());
        }
        return vo;
    }

    /**
     * 实体转换
     *
     * @param item
     * @return
     */
    private ConfigItemDetailVo entityToVo(TConfigItem item) {
        ConfigItemDetailVo vo = new ConfigItemDetailVo();
        vo.setId(item.getId());
        vo.setItemName(item.getItemName());
        vo.setHospitalId(item.getHospitalId());
        vo.setCommission(item.getCommission());
        vo.setStatus(item.getStatus());
        vo.setCreateTime(DateBuilder.formatDate(item.getCreateTime(), DateBuilder.FORMAT_FULL));
        vo.setUpdateTime(DateBuilder.formatDate(item.getUpdateTime(), DateBuilder.FORMAT_FULL));
        return vo;
    }


    @Override
    public ConfigItemDetailVo getItemConfigDeail(Long id) {
        logger.info("getItemConfigDeail start id:{}", id);
        TConfigItem item = configItemDao.selectInfoById(id);
        if (Objects.isNull(item)) {
            return null;
        }
        ConfigItemDetailVo vo = setConfigItemDetailVo(item);
        logger.info("getItemConfigDeail end id:{}, infoVo:{}", id, vo);
        return vo;
    }

    @Override
    public boolean addItemConfig(ConfigItemAddVo addVo) {
        logger.info("addItemConfig start addVo:{}", addVo);
        TConfigItem item = new TConfigItem();
        item.setItemName(addVo.getItemName());
        item.setHospitalId(addVo.getHospitalId());
        item.setCommission(new BigDecimal(addVo.getCommission()));
        item.setStatus((byte) UserStatusEnum.ENABLED.getCode());
        int insert = configItemDao.insert(item);
        logger.info("addItemConfig end addVo:{}, insertres:{}", addVo, insert);
        return insert > 0;
    }

    @Override
    public boolean updateItemConfig(ConfigItemUpdateVo updateVo) {
        TConfigItem item = configItemDao.selectInfoById(updateVo.getId());
        if(Objects.isNull(item)){
            return false;
        }
        item.setItemName(updateVo.getItemName());
        item.setHospitalId(updateVo.getHospitalId());
        item.setCommission(new BigDecimal(updateVo.getCommission()));
        int result = configItemDao.updateItemConfig(item);
        return result > 0;
    }

    @Override
    public boolean deleteConfig(Long id) {
        TConfigItem item = new TConfigItem();
        item.setId(id);
        item.setStatus((byte) UserStatusEnum.DISABLE.getCode());
        int result = configItemDao.updateItemConfig(item);
        return result > 0;
    }

    @Override
    public List<ConfigItemDetailVo> getItemConfigListByHid(Long hid) {
        logger.info("getItemConfigListByHid start id:{}", hid);
        List<ConfigItemDetailVo> list = new ArrayList<>();
        List<TConfigItem> tConfigItems = configItemDao.selectItemConfigListByHid(hid);
        if(CollectionUtils.isEmpty(tConfigItems)){
            return list;
        }
        for(TConfigItem item : tConfigItems){
            ConfigItemDetailVo detailVo = entityToVo(item);
            list.add(detailVo);
        }
        logger.info("getItemConfigListByHid end id:{}", hid);
        return list;
    }
}
