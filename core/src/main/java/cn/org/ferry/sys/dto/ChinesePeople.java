package cn.org.ferry.sys.dto;

import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.mybatis.handler.SexHandler;
import cn.org.ferry.system.sysenum.Sex;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Table(name = "chinese_people")
public class ChinesePeople extends BaseDTO {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String code;

    private String name;

    @ColumnType(typeHandler = SexHandler.class)
    private Sex sex;

    private Date birthDate;
}
