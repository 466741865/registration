package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountStatisticsDay;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAccountStatisticsDayDao {
    int insert(TAccountStatisticsDay record);

    List<TAccountStatisticsDay> selectAll();

    int update(TAccountStatisticsDay record);

    int updateStatusBySid(@Param("sid") Long sid, @Param("status") Integer status);

    TAccountStatisticsDay selectDayById(@Param("id") Long id);

    List<TAccountStatisticsDay> selectDayBySid(@Param("sid") Long sid, @Param("status") Integer status);
}