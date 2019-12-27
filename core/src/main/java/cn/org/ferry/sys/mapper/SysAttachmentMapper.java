package cn.org.ferry.sys.mapper;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.system.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAttachmentMapper extends Mapper<SysAttachment> {
    /**
     * 查询附件信息
     * @param sourceKey 附件编码为必传参数
     * @param sourceType 附件类型为必传参数
     */
    SysAttachment queryBySourceTypeAndSourceKey(@Param("sourceType") String sourceType, @Param("sourceKey") String sourceKey);

    /**
     * 查询附件
     */
    List<SysAttachment> queryBySourceType(@Param("sourceType") String sourceType);
}
