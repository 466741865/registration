package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAccountRecordDao {
    int insert(TAccountRecord record);

    List<TAccountRecord> selectAll();

    /**
     * 查询list总量
     *
     * @param patientName
     * @return
     */
    int selectRecordListTotal(@Param("patientName") String patientName);

    /**
     * 查询list
     *
     * @param patientName
     * @return
     */
    List<TAccountRecord> selectRecordList(@Param("patientName") String patientName, @Param("index") int index, @Param("pageSize") int pageSize);

    int updateRecord(TAccountRecord hospital);

    TAccountRecord selectInfoById(@Param("id") Long id);
}