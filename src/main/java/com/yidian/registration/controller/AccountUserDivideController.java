package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.exception.BizException;
import com.yidian.registration.service.IAccountUserDivideService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.account.divide.AccountDivideBuildVo;
import com.yidian.registration.vo.account.divide.AccountDivideDetailVo;
import com.yidian.registration.vo.account.divide.AccountDivideVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author: QingHang
 * @Description: 开票记录
 * @Date: 2022/6/11 10:35
 */
@RestController
@RequestMapping("/account/user/devide")
public class AccountUserDivideController {

    Logger logger = LoggerFactory.getLogger(AccountUserDivideController.class);

    @Resource
    private IAccountUserDivideService accountUserDivideService;

    @RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
    public PageVo<List<AccountDivideVo>> getUserDivideList(String settleDate, Long belongId, Integer page, Integer limit) {
        logger.info("[getUserDivideList]获取记录列表,start， settleDate={}, belongId:{}, pageNo={}, pageSize={}", settleDate, belongId, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<AccountDivideVo>> pageVo = accountUserDivideService.getAccountDivideList(settleDate, belongId, page, limit);
        pageVo.setPageNum(page);
        pageVo.setPageSize(limit);
        logger.info("[getUserDivideList]获取记录列表,end，settleDate={}, belongId:{}, pageNo={}, pageSize={}, res:{}", settleDate, belongId, page, limit, JSON.toJSON(pageVo));
        return pageVo;

    }

    /**
     * 查询详情信息
     *
     * @param did 记录id
     * @return
     */
    @RequestMapping(value = "/getInfo", produces = "application/json;charset=UTF-8")
    public ResultVo<AccountDivideVo> getInfo(Long did) {
        logger.info("[getInfo]查询详情信息，sid={}", did);
        if (Tools.isNull(did) || did <= 0) {
            logger.info("[getInfo]查询详情信息，参数存在空值");
            return new ResultVo<>(-1, "请选择分成账单");
        }
        AccountDivideVo accountDivideDeail = accountUserDivideService.getAccountDivideDeail(did);
        logger.info("[getInfo]查询详情信息,end,sid={},res={}", did, JSON.toJSON(accountDivideDeail));
        return new ResultVo<>(accountDivideDeail);
    }

    /**
     * 查询账单项目明细信息
     *
     * @param did 记录id
     * @return
     */
    @RequestMapping(value = "/getItemDetail", produces = "application/json;charset=UTF-8")
    public PageVo<List<AccountDivideDetailVo>> getItemDetail(Long did, Integer type) {
        logger.info("[getItemDetail]查询分成项目明细信息，did={}", did);
        if (Tools.isNull(did) || did <= 0) {
            logger.info("[getItemDetail]查询分成项目明细信息，参数存在空值");
            return new PageVo<>(-1, "请选择分成账单", null);
        }
        PageVo<List<AccountDivideDetailVo>> pageVo = accountUserDivideService.getItemDetail(did, type);
        logger.info("[getItemDetail]查询分成项目明细信息,end,sid={},pageVo={}", did, JSON.toJSON(pageVo));
        return pageVo;
    }

    /**
     * 生成账单
     *
     * @param buildVo 参数
     * @return
     */
    @RequestMapping(value = "/generateDivide", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> generateDivide(AccountDivideBuildVo buildVo) {
        logger.info("[generateDivide]生成账单，buildVo:{}", buildVo);
        if (Objects.isNull(buildVo) || StringUtils.isBlank(buildVo.getSettleDate())) {
            logger.info("[generateDivide]生成账单，参数存在空值");
            return new ResultVo<>(-1, "请选择月份");
        }
        Boolean result = null;
        try {
            result = accountUserDivideService.generateDivide(buildVo.getSettleDate(), buildVo.getBelongId());
        } catch (BizException e) {
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), e.getMessage());
        } catch (Exception e){
            logger.error("[generateDivide]生成账单失败，buildVo:{}", buildVo, e);
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), "分成账单生成失败");
        }
        logger.info("[generateDivide]生成账单,end, buildVo:{}, res={}", buildVo, result);
        return new ResultVo<>(result);
    }

    /**
     * 重新生成账单
     *
     * @param did 账单id
     * @return
     */
    @RequestMapping(value = "/rebuild", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> rebuild(Long did) {
        logger.info("[rebuild]重新生成分成账单，did={}", did);
        if (Tools.isNull(did) || did <= 0) {
            logger.info("[rebuild]重新生成分成账单，参数存在空值");
            return new ResultVo<>(-1, "请选择分成账单");
        }
        Boolean result = null;
        try {
            result = accountUserDivideService.rebuild(did);
        } catch (BizException e) {
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), e.getMessage());
        } catch (Exception e){
            logger.error("[rebuild]重新生成分成账单失败，sid:{}", did, e);
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), "分成账单生成失败");
        }
        logger.info("[rebuild]重新生成分成账单,end,sid={},res={}", did, result);
        return new ResultVo<>(result);
    }


}
