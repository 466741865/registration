package com.yidian.registration.dao;

import com.yidian.registration.entity.TRegistrationConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TRegistrationConfigDao {

    int insert(TRegistrationConfig record);

    List<TRegistrationConfig> selectAll();

    /**
     * 分页查询配置列表
     *
     * @param day
     * @param index
     * @param pageSize
     * @return
     */
    List<TRegistrationConfig> getConfigList(@Param("day") Long day, @Param("index") int index, @Param("pageSize") int pageSize);

    /**
     * 查询list总量
     * @param day
     * @return
     */
    int getConfigListTotal(@Param("day") Long day);

    /**
     * 根据id查询配置
     *
     * @param id
     * @return
     */
    TRegistrationConfig getConfigById(Long id);

    /**
     * 根据id进行删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

}