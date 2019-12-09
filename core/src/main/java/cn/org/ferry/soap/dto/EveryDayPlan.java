package cn.org.ferry.soap.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>每日还款计划实体类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:39
 */

@XmlRootElement(name = "PROJECT")
@XmlType(propOrder = {"PARTNERS", "PARTNERS_CONTRACT_NUMBER", "DEPOSIT_TOTAL", "DEPOSIT_BALANCE"})
public class EveryDayPlan {

    private String PARTNERS;

    private String PARTNERS_CONTRACT_NUMBER;

    private String DEPOSIT_TOTAL;

    private String DEPOSIT_BALANCE;

    @XmlElement(required = true)
    public String getPARTNERS() {
        return PARTNERS;
    }

    public void setPARTNERS(String PARTNERS) {
        this.PARTNERS = PARTNERS;
    }

    @XmlElement(required = true)
    public String getPARTNERS_CONTRACT_NUMBER() {
        return PARTNERS_CONTRACT_NUMBER;
    }

    public void setPARTNERS_CONTRACT_NUMBER(String PARTNERS_CONTRACT_NUMBER) {
        this.PARTNERS_CONTRACT_NUMBER = PARTNERS_CONTRACT_NUMBER;
    }

    @XmlElement(required = true)
    public String getDEPOSIT_TOTAL() {
        return DEPOSIT_TOTAL;
    }

    public void setDEPOSIT_TOTAL(String DEPOSIT_TOTAL) {
        this.DEPOSIT_TOTAL = DEPOSIT_TOTAL;
    }

    @XmlElement(required = true)
    public String getDEPOSIT_BALANCE() {
        return DEPOSIT_BALANCE;
    }

    public void setDEPOSIT_BALANCE(String DEPOSIT_BALANCE) {
        this.DEPOSIT_BALANCE = DEPOSIT_BALANCE;
    }
}
