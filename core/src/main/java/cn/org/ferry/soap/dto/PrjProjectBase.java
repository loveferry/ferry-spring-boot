package cn.org.ferry.soap.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>签约投放接口消息体
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:39
 */

@XmlType(propOrder = {"PROJECT", "CASHFLOW_LIST"})
public class PrjProjectBase {
    private PrjProject PROJECT;
    private List<Cashflow> CASHFLOW_LIST;

    @XmlElement(required = true)
    public PrjProject getPROJECT() {
        return PROJECT;
    }

    public void setPROJECT(PrjProject PROJECT) {
        this.PROJECT = PROJECT;
    }

    @XmlElementWrapper(name = "list")
//    @XmlAnyElement
    public List<Cashflow> getCASHFLOW_LIST() {
        return CASHFLOW_LIST;
    }

    public void setCASHFLOW_LIST(List<Cashflow> CASHFLOW_LIST) {
        this.CASHFLOW_LIST = CASHFLOW_LIST;
    }
}
