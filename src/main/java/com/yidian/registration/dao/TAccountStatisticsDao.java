package com.yidian.registration.dao;

import com.yidian.registration.entity.TAccountStatistics;
import java.util.List;

public interface TAccountStatisticsDao {
    int insert(TAccountStatistics record);

    List<TAccountStatistics> selectAll();
}