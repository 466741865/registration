package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountStatisticsDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAccountStatisticsDetailDao {
    int insert(TAccountStatisticsDetail record);

    int batchInsert(@Param("records") List<TAccountStatisticsDetail> records);

    List<TAccountStatisticsDetail> selectAll();

    List<TAccountStatisticsDetail> selectDetailBySid(@Param("sid") Long sid);

    int deleteBySid(@Param("sid") Long sid);

}