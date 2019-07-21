package cn.org.ferry.sys.mapper;

import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.system.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysFileMapper extends BaseMapper<SysFile> {
    /**
     * 查询附件
     */
    List<SysFile> query(SysFile SysFile);

    /**
     * 查询文件
     */
    List<SysFile> queryByAttachmentId(@Param("attachmentId") Long attachmentId);
}
