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
     * @param itemName
     * @return
     */
    int selectItemListTotal(@Param("itemName") String itemName);

    /**
     * 查询list
     *
     * @param itemName
     * @return
     */
    List<TConfigItem> selectItemConfigList(@Param("itemName") String itemName, @Param("index") int index, @Param("pageSize") int pageSize);

    int updateItemConfig(TConfigItem hospital);

    TConfigItem selectInfoById(@Param("id") Long id);

    List<TConfigItem> selectItemConfigListByHid(@Param("hid") Long hid);

}