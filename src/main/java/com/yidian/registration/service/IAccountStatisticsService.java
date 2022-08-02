package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.account.statistics.AccountStatisticsDetailVo;
import com.yidian.registration.vo.account.statistics.AccountStatisticsVo;
import com.yidian.registration.vo.account.statistics.TAccountStatisticsDayDetailVO;
import com.yidian.registration.vo.account.statistics.TAccountStatisticsDayVO;

import java.util.List;

/**
 * @Author: QingHang
 * @Description: 项目配置
 * @Date: 2022/6/11 10:31
 */
public interface IAccountStatisticsService {

    /**
     * 查询账单统计列表
     * @param settleDate
     * @param hospitalId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVo<List<AccountStatisticsVo>> getAccountStatisticsList(String settleDate, Long hospitalId, Integer pageNo, Integer pageSize);

    /**
     * 查询账单统计详情
     * @param id
     * @return
     */
    AccountStatisticsVo getAccountStatisticsDeail(Long id);

    /**
     * 查询账单项目明细
     * @param sid
     * @return
     */
    PageVo<List<AccountStatisticsDetailVo>> getItemDetail(Long sid);

    /**
     * 生成账单
     * @param settleDate
     * @return
     */
    boolean generateStatistics(String settleDate, Long hospitalId);

    /**
     * 重新生成账单
     * @param id
     * @return
     */
    boolean rebuild(Long id);

    /**
     * 获取月每日统计
     * @param sid
     * @return
     */
    List<TAccountStatisticsDayVO> getDayList(Long sid);

    /**
     * 获取月每日统计详情
     * @param dayId
     * @return
     */
    List<TAccountStatisticsDayDetailVO> getDayDetail(Long dayId);

}
