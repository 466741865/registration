package com.yidian.registration.dao;

import com.yidian.registration.entity.TConfigItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TConfigItemDao {
    int insert(TConfigItem record);

    List<TConfigItem> selectAll();

    /**
     * 查询list总量
     *
     * @param name
     * @return
     */
    int selectItemListTotal(@Param("name") String name);

    /**
     * 查询list
     *
     * @param name
     * @return
     */
    List<TConfigItem> selectItemConfigList(@Param("name") String name, @Param("index") int index, @Param("pageSize") int pageSize);

    int updateItemConfig(TConfigItem hospital);

    TConfigItem selectInfoById(@Param("id") Long id);

    List<TConfigItem> selectItemConfigListByHid(@Param("hid") Long hid);

}