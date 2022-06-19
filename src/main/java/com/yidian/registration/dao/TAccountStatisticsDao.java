package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAccountStatisticsDao {
    int insert(TAccountStatistics record);

    List<TAccountStatistics> selectAll();

    /**
     * 查询list总量
     *
     * @param hospitalId
     * @param month
     * @return
     */
    int selectListTotal(@Param("hospitalId") Long hospitalId, @Param("month") String month);

    /**
     * 查询list
     *
     * @param hospitalId
     * @param month
     * @param index
     * @param pageSize
     * @return
     */
    List<TAccountStatistics> selectList(@Param("hospitalId") Long hospitalId, @Param("month") String month, @Param("index") int index, @Param("pageSize") int pageSize);

    TAccountStatistics selectInfoById(@Param("id") Long id);

    List<TAccountStatistics> selectInfoByMonth(@Param("month") String month, @Param("hospitalId") Long hospitalId);

    int deleteById(@Param("id") Long id);
}