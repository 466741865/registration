package com.yidian.registration.service;

import com.yidian.registration.vo.PageVo;
import com.yidian.registration.vo.registration.RegistrationVo;

import java.util.Date;
import java.util.List;

/**
 * @Author: QingHang
 * @Description: TODO 挂号
 * @Date: 2021/3/6 23:24
 */
public interface IRegistrationService {

    /**
     * 查询列表
     * @param name 姓名
     * @param mobile 手机号
     * @param day 治疗日期
     * @param pageNo 分页
     * @param pageSize 页大小
     * @return
     */
    PageVo<List<RegistrationVo>> getRegistrationList(String name, String mobile, Date day, Integer pageNo , Integer pageSize);

    /**
     * 取消预约
     * @param id
     * @return
     */
    Boolean operation(Long id, Integer operationType);

}
