package com.yidian.registration.dao;

import com.yidian.registration.entity.TPatientCureEntity;
import com.yidian.registration.entity.TPatientUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TPatientCureDao {
    int insert(TPatientCureEntity record);

    List<TPatientCureEntity> selectAll();

    TPatientCureEntity getCureById(@Param("id") Long id);

    int update(TPatientCureEntity entity);

    int updateCureStatusById(@Param("id") Long id, @Param("status") int status, @Param("items") String items);

    /**
     * 列表
     * @param pids
     * @param cureDate
     * @param index
     * @param pageSize
     * @return
     */
    List<TPatientCureEntity> getCureList(@Param("pids") List<Long> pids, @Param("cureDate") Long cureDate, @Param("index") int index, @Param("pageSize") int pageSize);

    /**
     * 查询list总量
     * @param pids
     * @return
     */
    int getCureListTotal(@Param("pids") List<Long> pids, @Param("cureDate") Long cureDate);



}