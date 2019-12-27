package cn.org.ferry.sys.mapper;

import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.system.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysFileMapper extends Mapper<SysFile> {
    /**
     * 查询附件
     */
    List<SysFile> query(SysFile SysFile);

    /**
     * 查询文件
     */
    List<SysFile> queryByAttachmentId(@Param("attachmentId") Long attachmentId);

    /**
     * 根据附件类型，附件编码查询文件列表
     * @param sourceType 附件类型
     * @param sourceKey 附件编码
     * @return 文件列表
     */
    List<SysFile> queryBySourceTypeAndSourceKey(@Param("sourceType") String sourceType, @Param("sourceKey") String sourceKey);
}
