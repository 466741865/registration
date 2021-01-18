package com.yidian.registration.dao;


import com.yidian.registration.entity.SysLogEntity;

import java.util.List;

public interface SysLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(SysLogEntity record);

    SysLogEntity selectByPrimaryKey(Long id);

    List<SysLogEntity> selectAll();

    int updateByPrimaryKey(SysLogEntity record);
}