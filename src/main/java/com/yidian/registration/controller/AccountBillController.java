package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.service.IAccountRecordService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.account.record.AccountRecordAddVo;
import com.yidian.registration.vo.account.record.AccountRecordDetailVo;
import com.yidian.registration.vo.account.record.AccountRecordUpdateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: QingHang
 * @Description: 开票记录
 * @Date: 2022/6/11 10:35
 */
@RestController
@RequestMapping("/account/bill")
public class AccountBillController {

    Logger logger = LoggerFactory.getLogger(AccountBillController.class);

    @Resource
    private IAccountRecordService AccountRecordService;

    @RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
    public PageVo<List<AccountRecordDetailVo>> getBillList(String name, String settleDate, Long hospitalId, Long belongId, Integer page, Integer limit) {
        logger.info("[getRecordList]获取记录列表,start，name={}, settleDate={}, hospitalId:{}, belongId:{}, pageNo={}, pageSize={}", name, settleDate, hospitalId, belongId, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<AccountRecordDetailVo>> pageVo = AccountRecordService.getAccountRecordList(name, settleDate, hospitalId, belongId, page, limit);
        pageVo.setPageNum(page);
        pageVo.setPageSize(limit);
        logger.info("[getRecordList]获取记录列表,end，name={}, settleDate={}, hospitalId:{}, belongId:{}, pageNo={}, pageSize={}, res:{}", name, settleDate, hospitalId, belongId, page, limit, JSON.toJSON(pageVo));
        return pageVo;

    }

    /**
     * 添加记录
     *
     * @param addVo 添加记录
     * @return
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> addRecord(AccountRecordAddVo addVo) {
        logger.info("[addRecord]添加记录，userAddVo={}", JSON.toJSON(addVo));
        if (Tools.isNull(addVo) || Tools.isNull(addVo.getPatientName())
                || Tools.isNull(addVo.getInvoiceMoney())
                || Tools.isNull(addVo.getBelongId())
                || Tools.isNull(addVo.getItemId())
                || Tools.isNull(addVo.getHospitalId())) {
            logger.info("[addItem]添加记录，参数存在空值");
            return new ResultVo<>(-1, "请填写完整的信息");
        }
        Boolean res = AccountRecordService.addAccountRecord(addVo);
        logger.info("[addRecord]添加记录,end，userAddVo={},res={}", addVo.toString(), JSON.toJSON(res));
        return new ResultVo<>(res);
    }

    /**
     * 查询详情信息
     *
     * @param rid 记录id
     * @return
     */
    @RequestMapping(value = "/getInfo", produces = "application/json;charset=UTF-8")
    public ResultVo<AccountRecordDetailVo> getInfo(Long rid) {
        logger.info("[getInfo]查询详情信息，rid={}", rid);
        if (Tools.isNull(rid) || rid <= 0) {
            logger.info("[getInfo]查询详情信息，参数存在空值");
            return new ResultVo<>(-1, "请选择单位");
        }
        AccountRecordDetailVo configDeail = AccountRecordService.getAccountRecordDeail(rid);
        logger.info("[getInfo]查询详情信息,end,rid={},res={}", rid, JSON.toJSON(configDeail));
        return new ResultVo<>(configDeail);
    }

    /**
     * 修改信息
     *
     * @param updateVo
     * @return
     */
    @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> updateInfo(AccountRecordUpdateVo updateVo) {
        logger.info("[updateInfo]修改信息，updateVo={}", updateVo);
        if (Tools.isNull(updateVo) || updateVo.getId() <= 0) {
            logger.info("[updateInfo]修改信息，参数存在空值");
            return new ResultVo<>(-1, "请选择单位");
        }
        boolean result = AccountRecordService.updateAccountRecord(updateVo);
        logger.info("[updateInfo]修改信息,end,updateVo={},res={}", updateVo, result);
        return new ResultVo<>(result);
    }

    /**
     * 删除
     *
     * @param rid 记录id
     * @return
     */
    @RequestMapping(value = "/delete", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> delete(Long rid) {
        logger.info("[delete]删除信息，rid={}", rid);
        if (Tools.isNull(rid) || rid <= 0) {
            logger.info("[delete]删除信息，参数存在空值");
            return new ResultVo<>(-1, "请选择单位");
        }
        Boolean result = AccountRecordService.deleteConfig(rid);
        logger.info("[delete]删除信息,end,rid={},res={}", rid, result);
        return new ResultVo<>(result);
    }


}
