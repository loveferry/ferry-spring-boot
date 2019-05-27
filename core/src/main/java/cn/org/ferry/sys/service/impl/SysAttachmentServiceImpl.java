package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.sys.mapper.SysAttachmentMapper;
import cn.org.ferry.sys.service.SysAttachmentService;
import cn.org.ferry.sys.service.SysFileService;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachment> implements SysAttachmentService {
    @Autowired
    private SysAttachmentMapper iSysAttachmentMapper;

    @Autowired
    private SysFileService sysFileService;

    @Override
    public List<SysFile> query(SysAttachment sysAttachment) {
        List<SysAttachment> list = iSysAttachmentMapper.select(sysAttachment);
        if(CollectionUtils.isEmpty(list)){
            logger.debug("附件查询为空!");
            return new ArrayList<>(0);
        }
        List<SysFile> sysFileList = new ArrayList<>(10);
        for(SysAttachment attachment : list){
            SysFile sysFile = new SysFile();
            sysFile.setAttachmentId(attachment.getAttachmentId());
            sysFileList.addAll(sysFileService.select(sysFile));
        }
        return sysFileList;
    }
}
