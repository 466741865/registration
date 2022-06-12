package com.yidian.registration.dao;

import com.yidian.registration.entity.TConfigHospital;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TConfigHospitalDao {
    int insert(TConfigHospital record);

    List<TConfigHospital> selectAll();

    /**
     * 查询list总量
     *
     * @param name
     * @return
     */
    int selectHospitalListTotal(@Param("name") String name);

    /**
     * 查询list
     *
     * @param name
     * @return
     */
    List<TConfigHospital> selectHospitalConfigList(@Param("name") String name, @Param("index") int index, @Param("pageSize") int pageSize);

    int updateHospitalConfig(TConfigHospital hospital);

    TConfigHospital selectInfoById(@Param("id") Long id);

}