package cn.org.ferry.sys.dto;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.sysenum.Sex;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@ApiModel(value = "人员信息", parent = BaseDTO.class)
@Data
@Table(name = "chinese_people")
@XmlRootElement(name = "chinesePeople")
@XmlType(propOrder = {"id", "name", "sex", "birthDate"})
public class ChinesePeople extends BaseDTO {
    @ApiModelProperty(value = "主键", position = 1, allowEmptyValue = true)
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    @ApiModelProperty(value = "性别", position = 3)
    private Sex sex;

    @ApiModelProperty(value = "出生日", position = 4)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date birthDate;
}
