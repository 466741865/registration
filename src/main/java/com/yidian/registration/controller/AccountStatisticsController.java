package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.exception.BizException;
import com.yidian.registration.service.IAccountStatisticsService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.account.statistics.AccountStatisticsBuildVo;
import com.yidian.registration.vo.account.statistics.AccountStatisticsDetailVo;
import com.yidian.registration.vo.account.statistics.AccountStatisticsVo;
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
@RequestMapping("/account/statistics")
public class AccountStatisticsController {

    Logger logger = LoggerFactory.getLogger(AccountStatisticsController.class);

    @Resource
    private IAccountStatisticsService accountStatisticsService;

    @RequestMapping(value = "/getList", produces = "application/json;charset=UTF-8")
    public PageVo<List<AccountStatisticsVo>> getStatisticsList(String settleDate, Long hospitalId, Integer page, Integer limit) {
        logger.info("[getStatisticsList]获取记录列表,start， settleDate={}, hospitalId:{}, pageNo={}, pageSize={}", settleDate, hospitalId, page, limit);
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<AccountStatisticsVo>> pageVo = accountStatisticsService.getAccountStatisticsList(settleDate, hospitalId, page, limit);
        pageVo.setPageNum(page);
        pageVo.setPageSize(limit);
        logger.info("[getStatisticsList]获取记录列表,end，settleDate={}, hospitalId:{}, pageNo={}, pageSize={}, res:{}", settleDate, hospitalId, page, limit, JSON.toJSON(pageVo));
        return pageVo;

    }

    /**
     * 查询详情信息
     *
     * @param sid 记录id
     * @return
     */
    @RequestMapping(value = "/getInfo", produces = "application/json;charset=UTF-8")
    public ResultVo<AccountStatisticsVo> getInfo(Long sid) {
        logger.info("[getInfo]查询详情信息，sid={}", sid);
        if (Tools.isNull(sid) || sid <= 0) {
            logger.info("[getInfo]查询详情信息，参数存在空值");
            return new ResultVo<>(-1, "请选择账单");
        }
        AccountStatisticsVo statisticsDeail = accountStatisticsService.getAccountStatisticsDeail(sid);
        logger.info("[getInfo]查询详情信息,end,sid={},res={}", sid, JSON.toJSON(statisticsDeail));
        return new ResultVo<>(statisticsDeail);
    }

    /**
     * 查询账单项目明细信息
     *
     * @param sid 记录id
     * @return
     */
    @RequestMapping(value = "/getItemDetail", produces = "application/json;charset=UTF-8")
    public PageVo<List<AccountStatisticsDetailVo>> getItemDetail(Long sid) {
        logger.info("[getItemDetail]查询账单项目明细信息，sid={}", sid);
        if (Tools.isNull(sid) || sid <= 0) {
            logger.info("[getItemDetail]查询账单项目明细信息，参数存在空值");
            return new PageVo<>(-1, "请选择账单", null);
        }
        PageVo<List<AccountStatisticsDetailVo>> pageVo = accountStatisticsService.getItemDetail(sid);
        logger.info("[getItemDetail]查询账单项目明细信息,end,sid={},pageVo={}", sid, JSON.toJSON(pageVo));
        return pageVo;
    }

    /**
     * 生成账单
     *
     * @param buildVo 参数
     * @return
     */
    @RequestMapping(value = "/generateStatistics", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> generateStatistics(AccountStatisticsBuildVo buildVo) {
        logger.info("[generateStatistics]生成账单，buildVo:{}", buildVo);
        if (Objects.isNull(buildVo) || StringUtils.isBlank(buildVo.getSettleDate())) {
            logger.info("[generateStatistics]生成账单，参数存在空值");
            return new ResultVo<>(-1, "请选择月份");
        }
        Boolean result = null;
        try {
            result = accountStatisticsService.generateStatistics(buildVo.getSettleDate(), buildVo.getHospitalId());
        } catch (BizException e) {
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), e.getMessage());
        } catch (Exception e){
            logger.error("[generateStatistics]生成账单失败，buildVo:{}", buildVo, e);
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), "账单生成失败");
        }
        logger.info("[generateStatistics]生成账单,end, buildVo:{}, res={}", buildVo, result);
        return new ResultVo<>(result);
    }

    /**
     * 重新生成账单
     *
     * @param sid 账单id
     * @return
     */
    @RequestMapping(value = "/rebuild", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> rebuild(Long sid) {
        logger.info("[rebuild]重新生成账单，sid={}", sid);
        if (Tools.isNull(sid) || sid <= 0) {
            logger.info("[rebuild]重新生成账单，参数存在空值");
            return new ResultVo<>(-1, "请选择账单");
        }
        Boolean result = null;
        try {
            result = accountStatisticsService.rebuild(sid);
        } catch (BizException e) {
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), e.getMessage());
        } catch (Exception e){
            logger.error("[rebuild]重新生成账单失败，sid:{}", sid, e);
            return new ResultVo<>(StatusEnum.NODATA_CODE.getCode(), "账单生成失败");
        }
        logger.info("[rebuild]重新生成账单,end,sid={},res={}", sid, result);
        return new ResultVo<>(result);
    }


}
