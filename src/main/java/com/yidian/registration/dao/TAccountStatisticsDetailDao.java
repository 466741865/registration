package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountStatisticsDetail;
import java.util.List;

public interface TAccountStatisticsDetailDao {
    int insert(TAccountStatisticsDetail record);

    List<TAccountStatisticsDetail> selectAll();
}