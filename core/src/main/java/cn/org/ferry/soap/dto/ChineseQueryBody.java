package cn.org.ferry.soap.dto;

import javax.xml.bind.annotation.XmlElement;

/**
 * <p>人员查询接口消息体
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/09/26 19:00
 */
public class ChineseQueryBody {
    /**
     * 名称
     */
    private String name;

    @XmlElement(required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
