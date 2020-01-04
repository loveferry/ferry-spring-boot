package cn.org.ferry.soap.dto;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>每日还款计划现金流实体类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:39
 */

public class EveryPlanLine {
    private String TIMES;
    private String CF_DIRECTION;
    private String CF_ITEM;
    private String DUE_DATE;
    private String DUE_AMOUNT;
    private String PRINCIPAL;
    private String INTEREST;
    private String RECEIVED_AMOUNT;
    private String RECEIVED_PRINCIPAL;
    private String RECEIVED_INTEREST;

    @XmlElement(required = true)
    public String getTIMES() {
        return TIMES;
    }

    public void setTIMES(String TIMES) {
        this.TIMES = TIMES;
    }

    @XmlElement(required = true)
    public String getCF_DIRECTION() {
        return CF_DIRECTION;
    }

    public void setCF_DIRECTION(String CF_DIRECTION) {
        this.CF_DIRECTION = CF_DIRECTION;
    }

    @XmlElement(required = true)
    public String getCF_ITEM() {
        return CF_ITEM;
    }

    public void setCF_ITEM(String CF_ITEM) {
        this.CF_ITEM = CF_ITEM;
    }

    @XmlElement(required = true)
    public String getDUE_DATE() {
        return DUE_DATE;
    }

    public void setDUE_DATE(String DUE_DATE) {
        this.DUE_DATE = DUE_DATE;
    }

    @XmlElement(required = true)
    public String getDUE_AMOUNT() {
        return DUE_AMOUNT;
    }

    public void setDUE_AMOUNT(String DUE_AMOUNT) {
        this.DUE_AMOUNT = DUE_AMOUNT;
    }

    @XmlElement(required = true)
    public String getPRINCIPAL() {
        return PRINCIPAL;
    }

    public void setPRINCIPAL(String PRINCIPAL) {
        this.PRINCIPAL = PRINCIPAL;
    }

    @XmlElement(required = true)
    public String getINTEREST() {
        return INTEREST;
    }

    public void setINTEREST(String INTEREST) {
        this.INTEREST = INTEREST;
    }

    @XmlElement(required = true)
    public String getRECEIVED_AMOUNT() {
        return RECEIVED_AMOUNT;
    }

    public void setRECEIVED_AMOUNT(String RECEIVED_AMOUNT) {
        this.RECEIVED_AMOUNT = RECEIVED_AMOUNT;
    }

    @XmlElement(required = true)
    public String getRECEIVED_PRINCIPAL() {
        return RECEIVED_PRINCIPAL;
    }

    public void setRECEIVED_PRINCIPAL(String RECEIVED_PRINCIPAL) {
        this.RECEIVED_PRINCIPAL = RECEIVED_PRINCIPAL;
    }

    @XmlElement(required = true)
    public String getRECEIVED_INTEREST() {
        return RECEIVED_INTEREST;
    }

    public void setRECEIVED_INTEREST(String RECEIVED_INTEREST) {
        this.RECEIVED_INTEREST = RECEIVED_INTEREST;
    }
}
