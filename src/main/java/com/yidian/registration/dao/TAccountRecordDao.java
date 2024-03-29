package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TAccountRecordDao {
    int insert(TAccountRecord record);

    int batchInsert(@Param("recordList") List<TAccountRecord> recordList);

    List<TAccountRecord> selectAll();

    /**
     * 查询list总量
     *
     * @param patientName
     * @return
     */
    int selectRecordListTotal(@Param("patientName") String patientName, @Param("settleDate") String settleDate, @Param("invoiceDate") String invoiceDate, @Param("hospitalId") Long hospitalId, @Param("itemId") Long itemId, @Param("belongId") Long belongId);

    /**
     * 查询list
     *
     * @param patientName
     * @return
     */
    List<TAccountRecord> selectRecordList(@Param("patientName") String patientName, @Param("settleDate") String settleDate, @Param("invoiceDate") String invoiceDate, @Param("hospitalId") Long hospitalId, @Param("itemId") Long itemId, @Param("belongId") Long belongId, @Param("index") int index, @Param("pageSize") int pageSize);

    int updateRecord(TAccountRecord hospital);

    TAccountRecord selectInfoById(@Param("id") Long id);

    BigDecimal calculateInvoiceAmount(@Param("settleDate") String settleDate, @Param("hospitalId") Long hospitalId, @Param("itemId") Long itemId, @Param("belongId") Long belongId, @Param("invoiceDate") String invoiceDate);

    List<String> selectInvoiceDate(@Param("settleDate") String settleDate, @Param("hospitalId") Long hospitalId);
}