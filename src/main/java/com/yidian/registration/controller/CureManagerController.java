package com.yidian.registration.controller;

import com.alibaba.fastjson.JSON;
import com.yidian.registration.constant.Constants;
import com.yidian.registration.enums.StatusEnum;
import com.yidian.registration.service.ICureService;
import com.yidian.registration.utils.Tools;
import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.ResultVo;
import com.yidian.registration.vo.cure.PatientCureAddVo;
import com.yidian.registration.vo.cure.PatientCureInfoVo;
import com.yidian.registration.vo.cure.PatientCureOperateVo;
import com.yidian.registration.vo.req.RegistrationListReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @Author: QingHang
 * @Description: 预约管理
 * @Date: 2021/1/21 23:23
 */
@RestController
@RequestMapping("/cure/manager")
public class CureManagerController {


    Logger logger = LoggerFactory.getLogger(CureManagerController.class);

    @Autowired
    private ICureService cureService;

    /**
     * 获取预约列表
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/getCureList", produces = "application/json;charset=UTF-8")
    public PageVo<List<PatientCureInfoVo>> getCureList(RegistrationListReq req) {
        logger.info("[getCureList]获取预约列表,start，req={}", req);
        if (Objects.isNull(req)) {
            return new PageVo<>(StatusEnum.FAIL_CODE.getCode(), StatusEnum.FAIL_CODE.getDesc(), null);
        }
        if (Tools.isNull(req.getPage()) || req.getPage() <= 0) {
            req.setPage(Constants.DEFAULT_PAGE_NO);
        }
        if (Tools.isNull(req.getLimit()) || req.getLimit() <= 0) {
            req.setLimit(Constants.DEFAULT_PAGE_SIZE);
        }
        PageVo<List<PatientCureInfoVo>> pageVos = cureService.getCureList(req.getName(), req.getMobile(), req.getDay(), req.getPage(), req.getLimit());
        logger.info("[getCureList]获取预约列表,end，req={}, res:{}", req, JSON.toJSON(pageVos));
        return pageVos;

    }

    /**
     * 添加预约
     *
     * @param addVo 添加参数
     * @return
     */

    @RequestMapping(value = "/addCure", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> addCure(PatientCureAddVo addVo) {
        logger.info("[addCure]添加预约，addVo={}", addVo);
        if (Tools.isNull(addVo) || Tools.isNull(addVo.getPid()) || Tools.isNull(addVo.getCureDate())) {
            logger.info("[addCure]添加预约，参数存在空值");
            return new ResultVo<>(-1, "请填写完整的信息");
        }
        ResultVo<Boolean> resultVo = cureService.addCure(addVo);
        logger.info("[addCure]添加预约,end,addVo :{}, res={}", addVo, JSON.toJSON(resultVo));
        return resultVo;
    }

    /**
     * 查询预约详情
     *
     * @param cid 预约id
     * @return
     */

    @RequestMapping(value = "/getInfoByCid", produces = "application/json;charset=UTF-8")
    public ResultVo<PatientCureInfoVo> getInfoByCid(Long cid) {
        logger.info("[getInfoByCid]查询预约详情，cid={}", cid);
        if (Tools.isNull(cid) || cid <= 0) {
            logger.info("[getInfoByCid]查询预约详情，参数存在空值");
            return new ResultVo<>(-1, "请填写完整的信息");
        }
        PatientCureInfoVo info = cureService.getInfoByCid(cid);
        logger.info("[getInfoByCid]查询预约详情,end,cid={}, res={}", cid, JSON.toJSON(info));
        return new ResultVo<>(info);
    }

    /**
     * 签到
     *
     * @param cureOperateVo 操作vo
     * @return
     */
    @RequestMapping(value = "/signIn", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> cureSignInOperation(PatientCureOperateVo cureOperateVo) {
        logger.info("[cureSignInOperation]签到操作，cureOperateVo={}", cureOperateVo);
        if (Tools.isNull(cureOperateVo) || cureOperateVo.getCureId() <= 0 ||
                Tools.isNull(cureOperateVo.getUseNum()) || cureOperateVo.getUseNum() <= 0) {
            logger.info("[cureSignInOperation]签到操作，参数存在空值");
            return new ResultVo<>(-1, "请输入正确的数值");
        }
        Boolean res = cureService.signIn(cureOperateVo);
        logger.info("[cureSignInOperation]签到操作,end,cureOperateVo={}， res={}", cureOperateVo, res);
        return new ResultVo<>(res);
    }

    /**
     * 操作
     *
     * @param cid
     * @param status
     * @return
     */
    @RequestMapping(value = "/operation", produces = "application/json;charset=UTF-8")
    public ResultVo<Boolean> operation(Long cid, Integer status) {
        logger.info("[operation]预约操作，cid={}, status={}", cid, status);
        if (Tools.isNull(cid) || cid <= 0) {
            logger.info("[operation]预约操作，参数存在空值");
            return new ResultVo<>(-1, "请选择账号");
        }
        ResultVo<Boolean> resultVo = cureService.operation(cid, status);
        logger.info("[operation]预约操作,end, cid={}, status={} res={}", cid, status, JSON.toJSON(resultVo));
        return resultVo;
    }

    /**
     * 获取预约列表-by-pid
     *
     * @param pid
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getCureListByPid", produces = "application/json;charset=UTF-8")
    public PageVo<List<PatientCureInfoVo>> getCureListByPid(Long pid, Integer page, Integer limit) {
        logger.info("[getCureListByPid]获取预约列表-by-pid列表,start，pid={}, page={}, limit={}", pid, page, limit);
        if (Objects.isNull(pid) || pid <= 0) {
            return new PageVo<>(StatusEnum.FAIL_CODE.getCode(), StatusEnum.FAIL_CODE.getDesc(), null);
        }
        if (Tools.isNull(page) || page <= 0) {
            page = Constants.DEFAULT_PAGE_NO;
        }
        if (Tools.isNull(limit) || limit <= 0) {
            limit = Constants.DEFAULT_PAGE_SIZE;
        }
        PageVo<List<PatientCureInfoVo>> pageVos = cureService.getCureListByPid(pid, page, limit);
        logger.info("[getCureListByPid]获取预约列表-by-pid列表,end，pid={}, page={}, limit={}, res:{}", pid, page, limit, JSON.toJSON(pageVos));
        return pageVos;
    }

}
