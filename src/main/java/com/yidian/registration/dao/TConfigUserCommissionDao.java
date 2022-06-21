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
     * @param name
     * @return
     */
    int selectConfigListTotal(@Param("name") String name);

    /**
     * 查询list
     *
     * @param name
     * @return
     */
    List<TConfigUserCommission> selectConfigList(@Param("name") String name, @Param("index") int index, @Param("pageSize") int pageSize);

    int updateConfig(TConfigUserCommission hospital);

    TConfigUserCommission selectInfoById(@Param("id") Long id);

    List<TConfigUserCommission> selectConfigListByIid(@Param("itemId") Long itemId, @Param("type") Integer type);

    List<TConfigUserCommission> selectUserCommissionConfigList(@Param("belongId") Long belongId, @Param("type") Integer type);

}