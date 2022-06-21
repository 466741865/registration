package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
    int selectRecordListTotal(@Param("patientName") String patientName, @Param("settleDate") String settleDate, @Param("hospitalId") Long hospitalId);

    /**
     * 查询list
     *
     * @param patientName
     * @return
     */
    List<TAccountRecord> selectRecordList(@Param("patientName") String patientName, @Param("settleDate") String settleDate, @Param("hospitalId") Long hospitalId, @Param("index") int index, @Param("pageSize") int pageSize);

    int updateRecord(TAccountRecord hospital);

    TAccountRecord selectInfoById(@Param("id") Long id);

    BigDecimal calculateInvoiceAmount(@Param("settleDate") String settleDate, @Param("hospitalId") Long hospitalId, @Param("itemId") Long itemId, @Param("belongId") Long belongId);
}