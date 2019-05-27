package cn.org.ferry.sys.service;

import cn.org.ferry.sys.dto.SysFile;
import cn.org.ferry.system.dto.BaseDTO;
import cn.org.ferry.system.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 文件处理的接口层
 */
public interface SysFileService extends BaseService<SysFile> {
    /**
     * 文件上传
     */
    boolean upload(List<MultipartFile> files, String sourceKey, String sourceType);

    /**
     * 文件下载
     */
    void fileDownload(HttpServletResponse httpServletResponse, Long fileId);

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
