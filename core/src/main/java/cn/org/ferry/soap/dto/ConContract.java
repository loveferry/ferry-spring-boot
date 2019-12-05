package cn.org.ferry.soap.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>合同实体类
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/29 09:39
 */

@XmlRootElement(name = "contract")
@XmlType(propOrder = {"PARTNERS", "PARTNERS_CONTRACT_NUMBER", "LEASE_START_DATE", "INCEPTION_OF_LEASE"})
public class ConContract {
    private String PARTNERS;
    private String PARTNERS_CONTRACT_NUMBER;
    private String LEASE_START_DATE;
    private String INCEPTION_OF_LEASE;

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
    public String getLEASE_START_DATE() {
        return LEASE_START_DATE;
    }

    public void setLEASE_START_DATE(String LEASE_START_DATE) {
        this.LEASE_START_DATE = LEASE_START_DATE;
    }

    @XmlElement(required = true)
    public String getINCEPTION_OF_LEASE() {
        return INCEPTION_OF_LEASE;
    }

    public void setINCEPTION_OF_LEASE(String INCEPTION_OF_LEASE) {
        this.INCEPTION_OF_LEASE = INCEPTION_OF_LEASE;
    }
}
