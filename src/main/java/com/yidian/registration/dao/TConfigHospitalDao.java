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
     * @param hospitalName
     * @return
     */
    int selectHospitalListTotal(@Param("hospitalName") String hospitalName);

    /**
     * 查询list
     *
     * @param hospitalName
     * @return
     */
    List<TConfigHospital> selectHospitalConfigList(@Param("hospitalName") String hospitalName, @Param("index") int index, @Param("pageSize") int pageSize);

    int updateHospitalConfig(TConfigHospital hospital);

    TConfigHospital selectInfoById(@Param("id") Long id);

    List<TConfigHospital> selectHospitalList(@Param("hospitalId") Long hospitalId, @Param("index") int index, @Param("pageSize") int pageSize);


}