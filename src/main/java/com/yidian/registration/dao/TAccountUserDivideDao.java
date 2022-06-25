package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountUserDivide;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAccountUserDivideDao {
    int insert(TAccountUserDivide record);

    List<TAccountUserDivide> selectAll();

    int update(TAccountUserDivide record);

    /**
     * 查询list总量
     *
     * @param belongId
     * @param month
     * @return
     */
    int selectListTotal(@Param("belongId") Long belongId, @Param("month") String month);

    /**
     * 查询list
     *
     * @param belongId
     * @param month
     * @param index
     * @param pageSize
     * @return
     */
    List<TAccountUserDivide> selectList(@Param("belongId") Long belongId, @Param("month") String month, @Param("index") int index, @Param("pageSize") int pageSize);

    TAccountUserDivide selectInfoById(@Param("id") Long id);

    List<TAccountUserDivide> selectInfoByMonth(@Param("belongId") Long belongId, @Param("month") String month);

}