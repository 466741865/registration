package com.yidian.registration.dao;

import com.yidian.registration.entity.TConfigUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TConfigUserDao {
    int insert(TConfigUser record);

    List<TConfigUser> selectAll();

    /**
     * 查询list总量
     *
     * @param name
     * @return
     */
    int selectConfigListTotal(@Param("name") String name, @Param("type") Integer type);

    /**
     * 查询list
     *
     * @param name
     * @return
     */
    List<TConfigUser> selectConfigList(@Param("name") String name, @Param("type") Integer type, @Param("index") int index, @Param("pageSize") int pageSize);

    TConfigUser selectInfoById(@Param("id") Long id);

    int updateConfig(TConfigUser user);

    List<TConfigUser> selectListByType(@Param("type") Integer type);
}