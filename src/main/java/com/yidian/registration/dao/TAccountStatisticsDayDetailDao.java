package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountStatisticsDayDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAccountStatisticsDayDetailDao {
    int insert(TAccountStatisticsDayDetail record);

    int batchInsert(@Param("records") List<TAccountStatisticsDayDetail> records);

    List<TAccountStatisticsDayDetail> selectAll();

    int updateStatusBySid(@Param("sid") Long sid, @Param("status") Integer status);

    List<TAccountStatisticsDayDetail> selectDayDetailBySid(@Param("sid") Long sid, @Param("month") String month);

    List<TAccountStatisticsDayDetail> selectDayAllItemBySid(@Param("sid") Long sid, @Param("month") String month);



}