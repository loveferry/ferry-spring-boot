package cn.org.ferry.sys.service.impl;

import cn.org.ferry.core.dto.BaseDTO;
import cn.org.ferry.core.mapper.Mapper;
import cn.org.ferry.core.service.BaseService;
import cn.org.ferry.core.service.impl.BaseServiceImpl;
import cn.org.ferry.mybatis.enums.IfOrNot;
import cn.org.ferry.sys.dto.SysGenerateTable;
import cn.org.ferry.sys.exceptions.FileException;
import cn.org.ferry.sys.mapper.SysGenerateTableMapper;
import cn.org.ferry.sys.service.SysGenerateTableService;
import cn.org.ferry.sys.utils.FileUtils;
import com.github.pagehelper.PageHelper;
import com.google.common.base.CaseFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@Slf4j
public class SysGenerateTableServiceImpl extends BaseServiceImpl<SysGenerateTable> implements SysGenerateTableService {
    /**
     * 日志对象
     */
    private static final Logger logger = getLogger(SysGenerateTableServiceImpl.class);

    @Resource
    private SysGenerateTableMapper sysGenerateTableMapper;

    private static final String JAVA_PATH = "src"+File.separator+"main"+File.separator+"java";
    private static final String RESOURCE_PATH = "src"+File.separator+"main"+File.separator+"resources";
    private static final String JAVA_SUFFIX = ".java";
    private static final String XML_SUFFIX = ".xml";

    @Override
    public void generate(SysGenerateTable sysGenerateTable) throws FileException {
        logger.info("generate code start...");
        sysGenerateTable.setTableComment(sysGenerateTableMapper.queryTablesByTableComment(sysGenerateTable.getTableName()).getTableComment());
        List<SysGenerateTable> list = sysGenerateTableMapper.queryTableColumnsByTableName(sysGenerateTable.getTableName());
        if(CollectionUtils.isEmpty(list)){
            throw new FileException("未发现表"+sysGenerateTable.getTableName()+"上的字段");
        }
        String dirPath = sysGenerateTable.getProjectPath()+File.separator+JAVA_PATH+File.separator+
                sysGenerateTable.getPackagePath().replace(".", File.separator);
        logger.info("generate directory is {}", dirPath);
        // 生成实体类文件
        if(IfOrNot.Y == sysGenerateTable.getEntityFlag()){
            String entity = buildEntity(sysGenerateTable, list);
            File entityFile = new File(dirPath+File.separator+"dto"+ File.separator+sysGenerateTable.getEntityName()+JAVA_SUFFIX);
            if(entityFile.exists()){
                throw new FileException("实体类已存在");
            }else{
                FileUtils.createFile(entityFile.getAbsolutePath());
                try {
                    OutputStream os = new FileOutputStream(entityFile);
                    os.write(entity.getBytes());
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    FileException fileException = new FileException("实体类文件创建异常!");
                    fileException.initCause(e);
                    throw fileException;
                }

            }
            logger.info("generate dto file end, the file path is {}", entityFile.getAbsolutePath());
        }
        // 生成mybatis接口类文件
        if(IfOrNot.Y == sysGenerateTable.getMapperJavaFlag()){
            String mapperJava = buildMapperJava(sysGenerateTable);
            File mapperJavaFile = new File(dirPath+File.separator+"mapper"+ File.separator+sysGenerateTable.getMapperJavaName()+JAVA_SUFFIX);
            if(mapperJavaFile.exists()){
                throw new FileException("mybatis接口类文件已存在");
            }else{
                FileUtils.createFile(mapperJavaFile.getAbsolutePath());
                try {
                    OutputStream os = new FileOutputStream(mapperJavaFile);
                    os.write(mapperJava.getBytes());
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    FileException fileException = new FileException("mybatis接口类文件创建异常!");
                    fileException.initCause(e);
                    throw fileException;
                }
            }
            logger.info("generate mybatis interface file end, the file path is {}", mapperJavaFile.getAbsolutePath());
        }
        // 生成mybatis xml文件
        if(IfOrNot.Y == sysGenerateTable.getMapperXmlFlag()){
            String resourcesDirPath = sysGenerateTable.getProjectPath()+File.separator+RESOURCE_PATH+File.separator+
                    sysGenerateTable.getPackagePath().replace(".", File.separator);
            String mapperXml = buildMapperXml(sysGenerateTable, list);
            File mapperXmlFile = new File(resourcesDirPath+File.separator+"mapper"+ File.separator+sysGenerateTable.getMapperXmlName()+XML_SUFFIX);
            if(mapperXmlFile.exists()){
                throw new FileException("mybatis xml文件已存在");
            }else{
                FileUtils.createFile(mapperXmlFile.getAbsolutePath());
                try {
                    OutputStream os = new FileOutputStream(mapperXmlFile);
                    os.write(mapperXml.getBytes());
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    FileException fileException = new FileException("mybatis xml文件创建异常!");
                    fileException.initCause(e);
                    throw fileException;
                }
            }
            logger.info("generate mybatis xml file end, the file path is {}", mapperXmlFile.getAbsolutePath());
        }
        // 生成业务接口
        if(IfOrNot.Y == sysGenerateTable.getServiceFlag()){
            String service = buildService(sysGenerateTable);
            File serviceFile = new File(dirPath+File.separator+"service"+ File.separator+sysGenerateTable.getServiceName()+JAVA_SUFFIX);
            if(serviceFile.exists()){
                throw new FileException("业务接口文件已存在");
            }else{
                FileUtils.createFile(serviceFile.getAbsolutePath());
                try {
                    OutputStream os = new FileOutputStream(serviceFile);
                    os.write(service.getBytes());
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    FileException fileException = new FileException("业务接口文件创建异常!");
                    fileException.initCause(e);
                    throw fileException;
                }
            }
            logger.info("generate service interface file end, the file path is {}", serviceFile.getAbsolutePath());
        }
        // 生成业务实现
        if(IfOrNot.Y == sysGenerateTable.getServiceImplFlag()){
            String serviceImpl = buildServiceImpl(sysGenerateTable);
            File serviceImplFile = new File(dirPath+File.separator+"service"+ File.separator+"impl"+
                    File.separator+sysGenerateTable.getServiceImplName()+JAVA_SUFFIX);
            if(serviceImplFile.exists()){
                throw new FileException("业务实现文件已存在");
            }else{
                FileUtils.createFile(serviceImplFile.getAbsolutePath());
                try {
                    OutputStream os = new FileOutputStream(serviceImplFile);
                    os.write(serviceImpl.getBytes());
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    FileException fileException = new FileException("业务实现文件创建异常!");
                    fileException.initCause(e);
                    throw fileException;
                }
            }
            logger.info("generate service impl file end, the file path is {}", serviceImplFile.getAbsolutePath());
        }
        // 生成控制器
        if(IfOrNot.Y == sysGenerateTable.getControllerFlag()){
            String controller = buildController(sysGenerateTable);
            File controllerFile = new File(dirPath+File.separator+"controllers"+
                    File.separator+sysGenerateTable.getControllerName()+JAVA_SUFFIX);
            if(controllerFile.exists()){
                throw new FileException("控制器文件已存在");
            }else{
                FileUtils.createFile(controllerFile.getAbsolutePath());
                try {
                    OutputStream os = new FileOutputStream(controllerFile);
                    os.write(controller.getBytes());
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    FileException fileException = new FileException("控制器文件创建异常!");
                    fileException.initCause(e);
                    throw fileException;
                }
            }
            logger.info("generate controller file end, the file path is {}", controllerFile.getAbsolutePath());
        }

        int count = sysGenerateTableMapper.insertSelective(sysGenerateTable);
        logger.info("generate code save {} record...", count);

    }

    /**
     * 构建实体类
     */
    private String buildEntity(SysGenerateTable sysGenerateTable, List<SysGenerateTable> list) {
        StringBuilder entityFile = new StringBuilder();
        StringBuilder packages = new StringBuilder();
        StringBuilder entityBody = new StringBuilder();
        StringBuilder getterAndSetter = new StringBuilder();
        Map<String, Boolean> map = new HashMap<>(5);
        for (SysGenerateTable generateTable : list) {
            // 注释
            if(StringUtils.isNotEmpty(generateTable.getColumnComment())){
                entityBody.append("\t/**\n\t * ").append(generateTable.getColumnComment()).append("\n\t */\n");
            }
            // 注解
            if(StringUtils.equals("PRI", generateTable.getColumnKey())){
                if(!map.getOrDefault("@Id", false)){
                    map.put("@Id", true);
                    packages.append("import ").append(Id.class.getName()).append(";\n");
                }
                if(!map.getOrDefault("@GeneratedValue", false)){
                    map.put("@GeneratedValue", true);
                    packages.append("import ").append(GeneratedValue.class.getName()).append(";\n");
                }
                entityBody.append("\t@Id\n\t@GeneratedValue\n");
            }/*else if(StringUtils.equals("NO", generateTable.getNullable())){
                if(!map.getOrDefault("@NotNull", false)){
                    map.put("@NotNull", true);
                    packages.append("import ").append(NotNull.class.getName()).append(";\n");
                }
                entityBody.append("\t@NotNull\n");
            }*/
            if(null != generateTable.getCharacterMaximumLength() && StringUtils.equals("varchar", generateTable.getDataType().toLowerCase())){
                if(!map.getOrDefault("@Length", false)){
                    map.put("@Length", true);
                    packages.append("import ").append(Length.class.getName()).append(";\n");
                }
                entityBody.append("\t@Length(max = ").append(generateTable.getCharacterMaximumLength()).append(")\n");
            }
            String javaColumnName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, generateTable.getColumnName().toLowerCase());
            String getMethodName = "get"+CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, javaColumnName);
            String setMethodName = "set"+CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, javaColumnName);
            String columnJavaType = String.class.getSimpleName();
            switch (generateTable.getDataType().toLowerCase()){
                case "int" :
                    columnJavaType = Integer.class.getSimpleName();
                    break;
                case "float" :
                    columnJavaType = Float.class.getSimpleName();
                    break;
                case "bigint" :
                    columnJavaType = Long.class.getSimpleName();
                    break;
                case "double" :
                    columnJavaType = Double.class.getSimpleName();
                    break;
                case "decimal" :
                    Class<BigDecimal> cls = BigDecimal.class;
                    if(!map.getOrDefault(cls.getSimpleName(), false)){
                        map.put(cls.getSimpleName(), true);
                        packages.append("import ").append(cls.getName()).append(";\n");
                    }
                    columnJavaType = cls.getSimpleName();
                    break;
                case "time" :
                    Class<LocalTime> localTimeClass = LocalTime.class;
                    if(!map.getOrDefault(localTimeClass.getSimpleName(), false)){
                        map.put(localTimeClass.getSimpleName(), true);
                        packages.append("import ").append(localTimeClass.getName()).append(";\n");
                    }
                    columnJavaType = localTimeClass.getSimpleName();
                    break;
                case "date" :
                    Class<LocalDate> localDateClass = LocalDate.class;
                    if(!map.getOrDefault(localDateClass.getSimpleName(), false)){
                        map.put(localDateClass.getSimpleName(), true);
                        packages.append("import ").append(localDateClass.getName()).append(";\n");
                    }
                    columnJavaType = localDateClass.getSimpleName();
                    break;
                case "datetime" :
                    Class<Date> dateClass = Date.class;
                    if(!map.getOrDefault(dateClass.getSimpleName(), false)){
                        map.put(dateClass.getSimpleName(), true);
                        packages.append("import ").append(dateClass.getName()).append(";\n");
                    }
                    columnJavaType = dateClass.getSimpleName();
                    break;
                case "enum" :
                case "char" :
                case "varchar" :
                default:
                    break;
            }
            entityBody.append("\tprivate ").append(columnJavaType).append(" ").append(javaColumnName).append(";\n\n");
            getterAndSetter.append("\tpublic void ").append(setMethodName).append("(").append(columnJavaType).append(" ").append(javaColumnName).append("){\n")
                    .append("\t\tthis.").append(javaColumnName).append(" = ").append(javaColumnName).append(";\n")
                    .append("\t}\n\n")
                    .append("\tpublic ").append(columnJavaType).append(" ").append(getMethodName).append("(){\n")
                    .append("\t\treturn this.").append(javaColumnName).append(";\n")
                    .append("\t}\n\n");
        }
        // 实体类
        entityFile.append("package ").append(sysGenerateTable.getPackagePath()).append(".dto;\n\n")
                .append("import ").append(BaseDTO.class.getName()).append(";\n")
                .append("import ").append(Table.class.getName()).append(";\n")
                .append(packages).append('\n')
                .append("/**\n * Generate by code generator\n")
                .append(" * ").append(Optional.ofNullable(sysGenerateTable.getTableComment()).orElse("")).append("\n")
                .append(" */\n\n")
                .append("@Table(name = \"").append(sysGenerateTable.getTableName()).append("\")\n")
                .append("public class ").append(sysGenerateTable.getEntityName()).append(" extends BaseDTO {\n")
                .append(entityBody).append(getterAndSetter).append("}");
        return entityFile.toString();
    }

    /**
     * 构建mybatis接口类
     */
    private String buildMapperJava(SysGenerateTable sysGenerateTable){
        StringBuilder mapperJavaFile = new StringBuilder();
        mapperJavaFile.append("package ").append(sysGenerateTable.getPackagePath()).append(".mapper;\n\n")
                .append("import ").append(sysGenerateTable.getPackagePath()).append(".dto.").append(sysGenerateTable.getEntityName()).append(";\n")
                .append("import ").append(Mapper.class.getName()).append(";\n")
                .append('\n')
                .append("/**\n * Generate by code generator\n")
                .append(" * ").append(Optional.ofNullable(sysGenerateTable.getTableComment()).orElse(""))
                .append(" mybatis 接口层").append("\n").append(" */\n")
                .append('\n')
                .append("public interface ").append(sysGenerateTable.getMapperJavaName())
                .append(" extends ").append("Mapper<").append(sysGenerateTable.getEntityName()).append(">").append("{\n")
                .append("\n")
                .append("}");
        return mapperJavaFile.toString();
    }

    /**
     * 构建mybatis xml文件
     */
    private String buildMapperXml(SysGenerateTable sysGenerateTable, List<SysGenerateTable> list){
        StringBuilder mapperXmlFile = new StringBuilder();
        mapperXmlFile.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
                .append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n")
                .append("<mapper namespace=\"").append(sysGenerateTable.getPackagePath())
                .append(".mapper.").append(sysGenerateTable.getMapperJavaName()).append("\">\n")
                .append("\t<resultMap id=\"BaseResultMap\" type=\"").append(sysGenerateTable.getPackagePath())
                .append(".dto.").append(sysGenerateTable.getEntityName()).append("\">\n");
        for (SysGenerateTable generateTable : list) {
            if(StringUtils.equals("PRI", generateTable.getColumnKey())){
                mapperXmlFile.append("\t\t<id ");
            }else{
                mapperXmlFile.append("\t\t<result ");
            }
            mapperXmlFile.append("column=\"").append(generateTable.getColumnName().toLowerCase()).append("\" property=\"")
                    .append(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, generateTable.getColumnName()))
                    .append("\" ");
            String columnJdbcType = "VARCHAR";
            String columnJavaType = String.class.getName();
            switch (generateTable.getDataType().toLowerCase()){
                case "int" :
                    columnJdbcType = "INT";
                    columnJavaType = Integer.class.getName();
                    break;
                case "float" :
                    columnJdbcType = "FLOAT";
                    columnJavaType = Float.class.getName();
                    break;
                case "bigint" :
                    columnJdbcType = "BIGINT";
                    columnJavaType = Long.class.getName();
                    break;
                case "double" :
                    columnJdbcType = "DOUBLE";
                    columnJavaType = Double.class.getName();
                    break;
                case "decimal" :
                    columnJdbcType = "DECIMAL";
                    columnJavaType = BigDecimal.class.getName();
                    break;
                case "time" :
                    columnJdbcType = "TIME";
                    columnJavaType = LocalTime.class.getName();
                    break;
                case "date" :
                    columnJdbcType = "DATE";
                    columnJavaType = LocalDate.class.getName();
                    break;
                case "datetime" :
                    columnJdbcType = "DATE";
                    columnJavaType = Date.class.getName();
                    break;
                case "enum" :
                case "char" :
                case "varchar" :
                default:
                    break;
            }
            mapperXmlFile.append("javaType=\"").append(columnJavaType).append("\" ")
                    .append("jdbcType=\"").append(columnJdbcType).append("\"/>\n");
        }
        mapperXmlFile.append("\t</resultMap>\n\n");
        mapperXmlFile.append("</mapper>");
        return mapperXmlFile.toString();
    }

    /**
     * 构建业务接口
     */
    private String buildService(SysGenerateTable sysGenerateTable){
        StringBuilder serviceFile = new StringBuilder();
        serviceFile.append("package ").append(sysGenerateTable.getPackagePath()).append(".service;\n\n")
                .append("import ").append(BaseService.class.getName()).append(";\n")
                .append("import ").append(sysGenerateTable.getPackagePath()).append(".dto.").append(sysGenerateTable.getEntityName()).append(";\n\n")
                .append("/**\n * Generate by code generator\n")
                .append(" * ").append(Optional.ofNullable(sysGenerateTable.getTableComment()).orElse(""))
                .append(" 业务接口").append("\n").append(" */\n\n")
                .append("public interface ").append(sysGenerateTable.getServiceName()).append(" extends BaseService<").append(sysGenerateTable.getEntityName()).append("> {\n")
                .append("\n")
                .append("}");
        return serviceFile.toString();
    }

    /**
     * 构建业务接口
     */
    private String buildServiceImpl(SysGenerateTable sysGenerateTable){
        return new StringBuilder().append("package ").append(sysGenerateTable.getPackagePath()).append(".service.impl;\n\n")
                .append("import ").append(Service.class.getName()).append(";\n")
                .append("import ").append(Logger.class.getName()).append(";\n")
                .append("import ").append(LoggerFactory.class.getName()).append(";\n")
                .append("import ").append(Autowired.class.getName()).append(";\n")
                .append("import ").append(BaseServiceImpl.class.getName()).append(";\n")
                .append("import ").append(sysGenerateTable.getPackagePath()).append(".dto.").append(sysGenerateTable.getEntityName()).append(";\n")
                .append("import ").append(sysGenerateTable.getPackagePath()).append(".mapper.").append(sysGenerateTable.getMapperJavaName()).append(";\n")
                .append("import ").append(sysGenerateTable.getPackagePath()).append(".service.").append(sysGenerateTable.getServiceName()).append(";\n\n")
                .append("/**\n * Generate by code generator\n")
                .append(" * ").append(Optional.ofNullable(sysGenerateTable.getTableComment()).orElse(""))
                .append(" 业务接口实现类").append("\n").append(" */\n\n")
                .append("@Service\n")
                .append("public class ").append(sysGenerateTable.getServiceImplName()).append(" extends BaseServiceImpl<")
                .append(sysGenerateTable.getEntityName()).append("> implements ").append(sysGenerateTable.getServiceName()).append(" {\n")
                .append("\t/**\n\t * 日志处理对象\n\t **/\n")
                .append("\tprivate static final Logger logger = LoggerFactory.getLogger(").append(sysGenerateTable.getServiceImplName()).append(".class);\n\n")
                .append("\t@Autowired\n").append("\tprivate ").append(sysGenerateTable.getMapperJavaName()).append(" ")
                .append(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, sysGenerateTable.getMapperJavaName())).append(";\n\n")
                .append("}").toString();
    }

    /**
     * 构建控制器
     */
    private String buildController(SysGenerateTable sysGenerateTable){
        String lowerEntityName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, sysGenerateTable.getEntityName());
        String lowerServiceName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, sysGenerateTable.getServiceName());
        StringBuilder controller = new StringBuilder();
        controller.append("package ").append(sysGenerateTable.getPackagePath()).append(".controllers;\n\n")
                .append("import ").append(RestController.class.getName()).append(";\n")
                .append("import ").append(RequestMapping.class.getName()).append(";\n")
                .append("import ").append(RequestParam.class.getName()).append(";\n")
                .append("import ").append(Autowired.class.getName()).append(";\n")
                .append("import ").append(Api.class.getName()).append(";\n")
                .append("import ").append(ApiOperation.class.getName()).append(";\n")
                .append("import ").append(ApiParam.class.getName()).append(";\n")
                .append("import ").append(List.class.getName()).append(";\n")
                .append("import ").append(sysGenerateTable.getPackagePath()).append(".dto.").append(sysGenerateTable.getEntityName()).append(";\n")
                .append("import ").append(sysGenerateTable.getPackagePath()).append(".service.").append(sysGenerateTable.getServiceName()).append(";\n")
                .append("import ").append(sysGenerateTable.getPackagePath()).append(".service.").append(sysGenerateTable.getServiceName()).append(";\n")
                .append('\n')
                .append("/**\n * Generate by code generator\n")
                .append(" * ").append(Optional.ofNullable(sysGenerateTable.getTableComment()).orElse(""))
                .append(" 控制器").append("\n").append(" */\n")
                .append('\n')
                .append("@Api(tags = \"").append(sysGenerateTable.getTableComment()).append("控制器\", position = 100)\n")
                .append("@RestController\n")
                .append("@RequestMapping(\"/api\")\n")
                .append("public class ").append(sysGenerateTable.getControllerName()).append(" {\n")
                .append("\t@Autowired\n")
                .append("\tprivate ").append(sysGenerateTable.getServiceName()).append(" ")
                .append(lowerServiceName).append(";\n")
                .append('\n')
                .append("\t/**\n\t * 查询\n\t */\n")
                .append("\t@ApiOperation(\"查询").append(sysGenerateTable.getTableComment()).append("\")\n")
                .append("\t@RequestMapping(\"/").append(
                            sysGenerateTable.getTableName().toLowerCase().replaceAll("_", "/")
                        ).append("/query").append("\")\n")
                .append("\tpublic List<").append(sysGenerateTable.getEntityName()).append("> query(")
                .append(sysGenerateTable.getEntityName()).append(" ").append(lowerEntityName).append(",\n")
                .append("\t\t\t\t\t\t\t\t@ApiParam(name = \"page\", value = \"当前页\") @RequestParam(defaultValue = \"1\") int page,\n")
                .append("\t\t\t\t\t\t\t\t@ApiParam(name = \"pageSize\", value = \"页面大小\") @RequestParam(defaultValue = \"10\") int pageSize){\n")
                .append("\t\treturn ").append(lowerServiceName).append(".select(").append(lowerEntityName).append(", page, pageSize);\n")
                .append("\t}\n")
                .append('\n')
                .append("}");
        return controller.toString();
    }

    @Override
    public List<SysGenerateTable> queryTableNames(String tableName, int page, int pageSize) {
        logger.info("query table name start");
        PageHelper.startPage(page, pageSize);
        List<SysGenerateTable> list = sysGenerateTableMapper.queryTableNames(tableName);
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(sysGenerateTable -> {
                String entityName = CaseFormat.LOWER_UNDERSCORE.to(
                        CaseFormat.UPPER_CAMEL, sysGenerateTable.getTableName().toLowerCase()
                );
                IfOrNot Y = IfOrNot.Y;
                sysGenerateTable.setEntityFlag(Y);
                sysGenerateTable.setEntityName(entityName);
                sysGenerateTable.setServiceFlag(Y);
                sysGenerateTable.setServiceName(entityName+"Service");
                sysGenerateTable.setServiceImplFlag(Y);
                sysGenerateTable.setServiceImplName(entityName+"ServiceImpl");
                sysGenerateTable.setMapperJavaFlag(Y);
                sysGenerateTable.setMapperJavaName(entityName+"Mapper");
                sysGenerateTable.setMapperXmlFlag(Y);
                sysGenerateTable.setMapperXmlName(entityName+"Mapper");
                sysGenerateTable.setControllerFlag(Y);
                sysGenerateTable.setControllerName(entityName+"Controller");
            });
        }
        return list;
    }
}
