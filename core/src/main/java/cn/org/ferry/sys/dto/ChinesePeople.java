package cn.org.ferry.sys.dto;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.mybatis.handler.SexHandler;
import cn.org.ferry.system.sysenum.Sex;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@Data
@Table(name = "chinese_people")
@XmlRootElement(name = "chinesePeople")
@XmlType(propOrder = {"id", "name", "sex", "birthDate"})
public class ChinesePeople extends BaseDTO {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String name;

    @ColumnType(typeHandler = SexHandler.class)
    private Sex sex;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date birthDate;
}
