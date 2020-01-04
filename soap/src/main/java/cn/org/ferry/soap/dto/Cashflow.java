package cn.org.ferry.soap.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>项目实体类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:39
 */

@XmlType(propOrder = {"TIMES","CF_DIRECTION","CF_ITEM","DUE_DATE","DUE_AMOUNT","PRINCIPAL","INTEREST","SURPLUS_RECEIVED_PRINCIPAL"})
public class Cashflow {
    private String TIMES;
    private String CF_DIRECTION;
    private String CF_ITEM;
    private String DUE_DATE;
    private String DUE_AMOUNT;
    private String PRINCIPAL;
    private String INTEREST;
    private String SURPLUS_RECEIVED_PRINCIPAL;

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
    public String getSURPLUS_RECEIVED_PRINCIPAL() {
        return SURPLUS_RECEIVED_PRINCIPAL;
    }

    public void setSURPLUS_RECEIVED_PRINCIPAL(String SURPLUS_RECEIVED_PRINCIPAL) {
        this.SURPLUS_RECEIVED_PRINCIPAL = SURPLUS_RECEIVED_PRINCIPAL;
    }
}
