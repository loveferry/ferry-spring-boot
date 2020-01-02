package cn.org.ferry.sys.mapper;


import cn.org.ferry.sys.dto.ChinesePeople;
import cn.org.ferry.system.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChinesePeopleMapper extends Mapper<ChinesePeople> {
    /**
     * 根据姓名模糊查询所有人员信息
     */
    List<ChinesePeople> queryByName(@Param("name") String name);
}
