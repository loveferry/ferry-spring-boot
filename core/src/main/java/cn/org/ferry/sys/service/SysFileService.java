package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.SysAttachment;
import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.exception.FileException;
import cn.org.ferry.system.service.BaseService;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件处理的接口层
 */
public interface SysFileService extends BaseService<SysFile> {
    /**
     * 文件上传
     */
    void upload(HttpServletRequest httpServletRequest, SysAttachment sysAttachment);

    /**
     * 文件下载
     */
    void download(HttpServletResponse httpServletResponse, Long fileId) throws FileException;

    /**
     * 向附件表，文件表插入记录
     * @param sourceType 附件类型
     * @param sourceKey 附件编码
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param contentType 文件类型
     */
    void insertFileAndAttachment(String sourceType, String sourceKey, String filePath, String fileName, String contentType);

    /**
     * 删除文件
     * @param attachmentId 参数必传，根据附件id删除sys_file表所有数据，并删除物理文件
     */
    void deleteByAttachmentId(Long attachmentId);

    /**
     * 删除文件表数据和物理文件
     * @param fileId 文件表主键,必传
     */
    void deleteFileByPrimaryKey(Long fileId);

    /**
     * 通过附件ID查询文件
     * @param attachmentId 附件id
     * @return 返回文件列表
     */
    List<SysFile> queryByAttachmentId(Long attachmentId);

    /**
     * 根据附件类型，附件编码查询文件列表
     * @param sourceType 附件类型
     * @param sourceKey 附件编码
     * @return 文件列表
     */
    List<SysFile> queryBySourceTypeAndSourceKey(String sourceType, String sourceKey);

    /**
     * excel 导出, poi实现的百万级数据导出
     * @param response 响应对象
     * @param list 导出的数据集合
     * @param config 导出的配置信息,格式要求为json字符串格式,里面包含字段的映射关系,字段宽度,对齐方式等.
     *              "{
     *               fileName : '',
     *               header : '',
     *               sheetSize : 100,
     *               sheetName : 'sheet页名称',
     *               columns : [
     *                  {
     *                      value : 'userName',
     *                      field : '用户名',
     *                      type : 'string',
     *                      width : 200,
     *                      align : 'left'
     *                  },{
     *                      value : 'userCode',
     *                      field : '用户编码',
     *                      type : 'String',
     *                      width : 400,
     *                      align : 'center'
     *                  },{
     *                      value : 'userId',
     *                      field : 'id',
     *                      type : 'numeric'
     *                      width : 100,
     *                      align : 'right'
     *                  }
     *               ]
     *              }"
     */
    void excelExport(HttpServletResponse response, List<? extends BaseDTO> list, String config);

    /**
     * excel 导出, poi实现的百万级数据导出
     * @param response 响应对象
     * @param map 每个数据页的键值对，键对应sheet页的sheetName，值对应该页的数据
     * @param config 导出的配置信息,格式要求为json字符串格式,里面包含字段的映射关系,字段宽度,对齐方式等
     */
    void excelExport(HttpServletResponse response, Map<String, List<? extends BaseDTO>> map, String config);
}
