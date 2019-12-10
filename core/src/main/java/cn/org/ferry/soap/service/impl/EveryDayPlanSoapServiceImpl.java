package cn.org.ferry.soap.service.impl;

import cn.org.ferry.soap.dto.EveryDayPlan;
import cn.org.ferry.soap.dto.EveryPlanBase;
import cn.org.ferry.soap.dto.InHeaderMessage;
import cn.org.ferry.soap.dto.OutHeaderMessage;
import cn.org.ferry.soap.service.EveryDayPlanSoapService;
import org.springframework.stereotype.Service;

/**
 * <p>基于 soap 协议的每日还款计划实现类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:48
 */

@Service
public class EveryDayPlanSoapServiceImpl implements EveryDayPlanSoapService {

    @Override
    public OutHeaderMessage<EveryDayPlan> everyPlan(InHeaderMessage inHeaderMessage, EveryPlanBase everyPlanBase) {
        return null;
    }
}
