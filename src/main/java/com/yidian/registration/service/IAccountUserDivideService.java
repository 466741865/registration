package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.account.divide.AccountDivideDetailVo;
import com.yidian.registration.vo.account.divide.AccountDivideVo;

import java.util.List;

/**
 * @Author: QingHang
 * @Description: TODO
 * @Date: 2022/6/19 23:06
 */
public interface IAccountUserDivideService {

    /**
     * 查询账单分成列表
     * @param settleDate
     * @param belongId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageVo<List<AccountDivideVo>> getAccountDivideList(String settleDate, Long belongId, Integer pageNo, Integer pageSize);

    /**
     * 查询账单分成详情
     * @param did
     * @return
     */
    AccountDivideVo getAccountDivideDeail(Long did);

    /**
     * 查询账单项目明细
     * @param did
     * @return
     */
    PageVo<List<AccountDivideDetailVo>> getItemDetail(Long did, Integer type);

    /**
     * 生成分成账单
     * @param settleDate
     * @return
     */
    boolean generateDivide(String settleDate, Long BelongId);

    /**
     * 重新生成账单
     * @param did
     * @return
     */
    boolean rebuild(Long did);

}
