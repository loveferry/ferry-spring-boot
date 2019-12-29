package cn.org.ferry.sys.mapper;

import cn.org.ferry.sys.dto.SysAttachmentCategory;
import cn.org.ferry.system.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAttachmentCategoryMapper extends Mapper<SysAttachmentCategory> {
    /**
     * 查询附件类型信息
     * @param sourceType 必传参数，此字段有唯一约束，只能查出一条数据，不存在返回空
     */
    SysAttachmentCategory queryBySourceType(@Param("sourceType") String sourceType);

    /**
     * 查询附件类型
     */
    List<SysAttachmentCategory> query(SysAttachmentCategory sysAttachmentCategory);

    /**
     * 插入附件
     */
    void insertOne(SysAttachmentCategory sysAttachmentCategory);
}
