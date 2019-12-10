package cn.org.ferry.soap.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>每日还款计划接口消息体
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:39
 */

@XmlType(propOrder = {"PROJECT","CASHFLOW_LIST"})
public class EveryPlanBase {
    private EveryDayPlan PROJECT;
    private List<EveryPlanLine> CASHFLOW_LIST;

    @XmlElement(required = true)
    public EveryDayPlan getPROJECT() {
        return PROJECT;
    }

    public void setPROJECT(EveryDayPlan PROJECT) {
        this.PROJECT = PROJECT;
    }

    @XmlElementWrapper(name = "LIST")
    public List<EveryPlanLine> getCASHFLOW_LIST() {
        return CASHFLOW_LIST;
    }

    public void setCASHFLOW_LIST(List<EveryPlanLine> CASHFLOW_LIST) {
        this.CASHFLOW_LIST = CASHFLOW_LIST;
    }
}
