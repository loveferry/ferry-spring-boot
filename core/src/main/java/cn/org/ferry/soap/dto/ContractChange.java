package cn.org.ferry.soap.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>合同变更实体类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/12/11 11:34
 */

@XmlType(propOrder = {"PARTNERS","PARTNERS_CONTRACT_NUMBER","CHANGE_TYPE","TIMES","DUE_DATE","OVERDUE_AMOUNT","OUTSTANDING_PRINCIPAL"
        ,"PERIOD_INTEREST","PERIOD_INTEREST_CHANGE","DEPOSIT_DEDUCT_FLAG","DEPOSIT","PENALTY_INTEREST","NOMINAL_PRICE"
        ,"PRE_AMOUNT","REPLY_AMOUNT","DESCRIPTION","CASHFLOW_LIST"})
public class ContractChange {
    /**
     * 合作方
     */
    private String PARTNERS;

    /**
     * 进件序号
     */
    private String PARTNERS_CONTRACT_NUMBER;

    /**
     * 变更类型
     */
    private String CHANGE_TYPE;

    /**
     * 变更期数
     */
    private Long TIMES;

    /**
     * 变更日
     */
    private String DUE_DATE;

    /**
     * 逾期租金
     */
    private String OVERDUE_AMOUNT;

    /**
     * 剩余本金
     */
    private Double OUTSTANDING_PRINCIPAL;

    /**
     * 期间利息
     */
    private Double PERIOD_INTEREST;

    /**
     * 期间利息变更
     */
    private Double PERIOD_INTEREST_CHANGE;

    /**
     * 保证金是否抵扣
     */
    private String DEPOSIT_DEDUCT_FLAG;

    /**
     * 保证金
     */
    private Double DEPOSIT;

    /**
     * 罚息金额
     */
    private Double PENALTY_INTEREST;

    /**
     * 名义货价
     */
    private Double NOMINAL_PRICE;

    /**
     * 提前结清/回购款
     */
    private Double PRE_AMOUNT;

    /**
     * 返利款
     */
    private Double REPLY_AMOUNT;

    /**
     * 变更原因
     */
    private String DESCRIPTION;

    /**
     * 现金流
     */
    private List<Cashflow> CASHFLOW_LIST;

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
    public String getCHANGE_TYPE() {
        return CHANGE_TYPE;
    }

    public void setCHANGE_TYPE(String CHANGE_TYPE) {
        this.CHANGE_TYPE = CHANGE_TYPE;
    }

    @XmlElement(required = true)
    public Long getTIMES() {
        return TIMES;
    }

    public void setTIMES(Long TIMES) {
        this.TIMES = TIMES;
    }

    @XmlElement(required = true)
    public String getDUE_DATE() {
        return DUE_DATE;
    }

    public void setDUE_DATE(String DUE_DATE) {
        this.DUE_DATE = DUE_DATE;
    }

    @XmlElement(required = true)
    public String getOVERDUE_AMOUNT() {
        return OVERDUE_AMOUNT;
    }

    public void setOVERDUE_AMOUNT(String OVERDUE_AMOUNT) {
        this.OVERDUE_AMOUNT = OVERDUE_AMOUNT;
    }

    @XmlElement(required = true)
    public Double getOUTSTANDING_PRINCIPAL() {
        return OUTSTANDING_PRINCIPAL;
    }

    public void setOUTSTANDING_PRINCIPAL(Double OUTSTANDING_PRINCIPAL) {
        this.OUTSTANDING_PRINCIPAL = OUTSTANDING_PRINCIPAL;
    }

    @XmlElement(required = true)
    public Double getPERIOD_INTEREST() {
        return PERIOD_INTEREST;
    }

    public void setPERIOD_INTEREST(Double PERIOD_INTEREST) {
        this.PERIOD_INTEREST = PERIOD_INTEREST;
    }

    @XmlElement(required = true)
    public Double getPERIOD_INTEREST_CHANGE() {
        return PERIOD_INTEREST_CHANGE;
    }

    public void setPERIOD_INTEREST_CHANGE(Double PERIOD_INTEREST_CHANGE) {
        this.PERIOD_INTEREST_CHANGE = PERIOD_INTEREST_CHANGE;
    }

    @XmlElement(required = true)
    public String getDEPOSIT_DEDUCT_FLAG() {
        return DEPOSIT_DEDUCT_FLAG;
    }

    public void setDEPOSIT_DEDUCT_FLAG(String DEPOSIT_DEDUCT_FLAG) {
        this.DEPOSIT_DEDUCT_FLAG = DEPOSIT_DEDUCT_FLAG;
    }

    @XmlElement(required = true)
    public Double getDEPOSIT() {
        return DEPOSIT;
    }

    public void setDEPOSIT(Double DEPOSIT) {
        this.DEPOSIT = DEPOSIT;
    }

    @XmlElement(required = true)
    public Double getPENALTY_INTEREST() {
        return PENALTY_INTEREST;
    }

    public void setPENALTY_INTEREST(Double PENALTY_INTEREST) {
        this.PENALTY_INTEREST = PENALTY_INTEREST;
    }

    @XmlElement(required = true)
    public Double getNOMINAL_PRICE() {
        return NOMINAL_PRICE;
    }

    public void setNOMINAL_PRICE(Double NOMINAL_PRICE) {
        this.NOMINAL_PRICE = NOMINAL_PRICE;
    }

    @XmlElement(required = true)
    public Double getPRE_AMOUNT() {
        return PRE_AMOUNT;
    }

    public void setPRE_AMOUNT(Double PRE_AMOUNT) {
        this.PRE_AMOUNT = PRE_AMOUNT;
    }

    @XmlElement(required = true)
    public Double getREPLY_AMOUNT() {
        return REPLY_AMOUNT;
    }

    public void setREPLY_AMOUNT(Double REPLY_AMOUNT) {
        this.REPLY_AMOUNT = REPLY_AMOUNT;
    }

    @XmlElement(required = true)
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    @XmlElementWrapper(name = "CASHFLOW_LIST", required = true)
    @XmlElement(required = true, name = "CASHFLOW")
    public List<Cashflow> getCASHFLOW_LIST() {
        return CASHFLOW_LIST;
    }

    public void setCASHFLOW_LIST(List<Cashflow> CASHFLOW_LIST) {
        this.CASHFLOW_LIST = CASHFLOW_LIST;
    }
}
