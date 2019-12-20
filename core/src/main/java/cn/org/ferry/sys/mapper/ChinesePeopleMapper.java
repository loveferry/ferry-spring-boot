package cn.org.ferry.sys.mapper;

import cn.org.ferry.sys.dto.ChinesePeople;
import cn.org.ferry.system.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChinesePeopleMapper extends Mapper<ChinesePeople> {
    /**
     * 根据身份证号码查询人员信息
     */
    @Select("select code from chinese_people where code like concat(#{code},'%')")
    List<ChinesePeople> queryByCode(@Param("code") String code);

    /**
     * 根据姓名模糊查询所有人员信息
     */
    List<ChinesePeople> queryByName(@Param("name") String name);
}
