package com.yidian.registration.dao;

import com.yidian.registration.entity.TPatientPaymentRecordsEntity;
import com.yidian.registration.entity.TPatientUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TPatientPaymentRecordsDao {
    int insert(TPatientPaymentRecordsEntity record);

    List<TPatientPaymentRecordsEntity> selectAll();

    /**
     * 列表
     *
     * @param pid
     * @param index
     * @param pageSize
     * @return
     */
    List<TPatientPaymentRecordsEntity> getPatientPayMentList(@Param("pid") Long pid, @Param("index") int index, @Param("pageSize") int pageSize);

    /**
     * 查询list总量
     *
     * @param pid
     * @return
     */
    int getPatientPayMentListTotal(@Param("pid") Long pid);

}