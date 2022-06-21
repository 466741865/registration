package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountStatisticsDetail;
import com.yidian.registration.entity.TAccountUserDivideDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TAccountUserDivideDetailDao {
    int insert(TAccountUserDivideDetail record);

    List<TAccountUserDivideDetail> selectAll();

    List<TAccountUserDivideDetail> selectDetailListByDid(@Param("did") Long did, @Param("type") Integer type);

    int batchInsert(@Param("records") List<TAccountUserDivideDetail> records);

    TAccountUserDivideDetail calculateItemInvoiceAmount(@Param("settleDate") String settleDate, @Param("did") Long did, @Param("cType") Integer cType);

}