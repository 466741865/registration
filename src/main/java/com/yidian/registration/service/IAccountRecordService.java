package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.account.record.AccountRecordAddVo;
import com.yidian.registration.vo.account.record.AccountRecordBatchAddVo;
import com.yidian.registration.vo.account.record.AccountRecordDetailVo;
import com.yidian.registration.vo.account.record.AccountRecordUpdateVo;

import java.util.List;

/**
 * @Author: QingHang
 * @Description: 项目配置
 * @Date: 2022/6/11 10:31
 */
public interface IAccountRecordService {

    /**
     * 查询配置列表
     * @param name
     * @return
     */
    PageVo<List<AccountRecordDetailVo>> getAccountRecordList(String name, String settleDate, Long hospitalId, Long itemId, Long belongId, Integer pageNo, Integer pageSize);

    /**
     * 查询配置详情
     * @param id
     * @return
     */
    AccountRecordDetailVo getAccountRecordDeail(Long id);

    /**
     * 添加配置
     * @param addVo
     * @return
     */
    boolean addAccountRecord(AccountRecordAddVo addVo);

    /**
     * 批量添加配置
     * @param addVo
     * @return
     */
    boolean batchAddRecord(AccountRecordBatchAddVo addVo);

    /**
     * 更新
     * @param updateVo
     * @return
     */
    boolean updateAccountRecord(AccountRecordUpdateVo updateVo);

    /**
     * 删除配置
     * @param id
     * @return
     */
    boolean deleteConfig(Long id);

}
