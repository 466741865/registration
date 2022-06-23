package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountUserDivideDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TAccountUserDivideDetailDao {
    int insert(TAccountUserDivideDetail record);

    List<TAccountUserDivideDetail> selectAll();

    int updateStatusByDid(@Param("did") Long did, @Param("status") Integer status);

    int updateDidByDid(@Param("oldDid") Long oldDid, @Param("newDid") Long newDid);

    List<TAccountUserDivideDetail> selectDetailListByDid(@Param("did") Long did, @Param("type") Integer type);

    int batchInsert(@Param("records") List<TAccountUserDivideDetail> records);

    TAccountUserDivideDetail calculateItemInvoiceAmount(@Param("settleDate") String settleDate, @Param("did") Long did, @Param("cType") Integer cType);

}