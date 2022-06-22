package com.yidian.registration.dao;

import com.yidian.registration.entity.TConfigUserCommission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TConfigUserCommissionDao {
    int insert(TConfigUserCommission record);

    List<TConfigUserCommission> selectAll();

    /**
     * 查询list总量
     *
     * @param belongId
     * @return
     */
    int selectConfigListTotal(@Param("belongId") Long belongId);

    /**
     * 查询list
     *
     * @param belongId
     * @return
     */
    List<TConfigUserCommission> selectConfigList(@Param("belongId") Long belongId, @Param("index") int index, @Param("pageSize") int pageSize);

    int updateConfig(TConfigUserCommission userCommission);

    TConfigUserCommission selectInfoById(@Param("id") Long id);

    List<TConfigUserCommission> selectConfigListByIid(@Param("itemId") Long itemId);

    List<TConfigUserCommission> selectUserCommissionConfigList(@Param("belongId") Long belongId);

    List<TConfigUserCommission> selectCommissionConfigList(@Param("belongId") Long belongId, @Param("hospitalId") Long hospitalId, @Param("itemId") Long itemId);

}