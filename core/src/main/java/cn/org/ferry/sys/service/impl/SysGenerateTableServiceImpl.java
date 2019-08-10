package cn.org.ferry.sys.service.impl;

import cn.org.ferry.sys.dto.SysEnumType;
import cn.org.ferry.sys.dto.SysGenerateTable;
import cn.org.ferry.sys.mapper.SysGenerateTableMapper;
import cn.org.ferry.sys.service.SysEnumTypeService;
import cn.org.ferry.sys.service.SysGenerateTableService;
import cn.org.ferry.system.dto.ResponseData;
import cn.org.ferry.system.exception.CommonException;
import cn.org.ferry.system.service.impl.BaseServiceImpl;
import cn.org.ferry.system.sysenum.IfOrNotFlag;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@Slf4j
public class SysGenerateTableServiceImpl extends BaseServiceImpl<SysGenerateTable> implements SysGenerateTableService {
    @Autowired
    private SysGenerateTableMapper sysGenerateTableMapper;
    @Autowired
    private SysEnumTypeService sysEnumTypeService;

    private static final String JAVA_PATH = "src"+File.separator+"main"+File.separator+"java";

    @Override
    public ResponseData generate(SysGenerateTable sysGenerateTable) throws IOException {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(false);
        responseData.setMessage("");

        sysGenerateTable.setTableComment(sysGenerateTableMapper.queryTablesByTableComment(sysGenerateTable.getTableName()).getTableComment());
        List<SysGenerateTable> list = sysGenerateTableMapper.queryTableColumnsByTableName(sysGenerateTable.getTableName());
        if(CollectionUtils.isEmpty(list)){
            log.error("未发现表 {} 上的字段", sysGenerateTable.getTableName());
            throw new CommonException("未发现表"+sysGenerateTable.getTableName()+"上的字段");
        }
        String dirPath = sysGenerateTable.getProjectPath()+File.separator+JAVA_PATH+File.separator+
                sysGenerateTable.getPackagePath().replace(".", File.separator);
        if(IfOrNotFlag.Y == sysGenerateTable.getEntityFlag()){
            String entity = buildEntity(sysGenerateTable, list);
            File entityFile = new File(dirPath+File.separator+"dto"+ File.separator+sysGenerateTable.getEntityName());
            if(entityFile.exists()){
                responseData.setMessage(responseData.getMessage()+"<br/>实体类已存在!");
            }else if(entityFile.createNewFile()){
                OutputStream os = new FileOutputStream(entityFile);
                os.write(entity.getBytes());
                os.flush();
                os.close();
            }else{
                responseData.setMessage(responseData.getMessage()+"<br/>实体类创建失败!");
            }
        }
        StringBuilder mapperJavaFile = new StringBuilder();
        StringBuilder mapperXmlFile = new StringBuilder();
        StringBuilder serviceFile = new StringBuilder();
        StringBuilder serviceImplFile = new StringBuilder();
        StringBuilder controllerFile = new StringBuilder();
        return responseData;
    }

    /**
     * 构建实体类
     */
    private String buildEntity(SysGenerateTable sysGenerateTable, List<SysGenerateTable> list){
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
                    packages.append("import javax.persistence.Id;\n");
                }
                if(!map.getOrDefault("@GeneratedValue", false)){
                    map.put("@GeneratedValue", true);
                    packages.append("import javax.persistence.GeneratedValue;\n");
                }
                entityBody.append("\t@Id\n\t@GeneratedValue\n");
            }else if(StringUtils.equals("NO", generateTable.getNullable())){
                if(!map.getOrDefault("@NotNull", false)){
                    map.put("@NotNull", true);
                    packages.append("import javax.validation.constraints.NotNull;\n");
                }
                entityBody.append("\t@NotNull\n");
            }
            if(null != generateTable.getCharacterMaximumLength() && StringUtils.equals("varchar", generateTable.getDataType().toLowerCase())){
                if(!map.getOrDefault("@Length", false)){
                    map.put("@Length", true);
                    packages.append("import org.hibernate.validator.constraints.Length;\n");
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
                    if(!map.getOrDefault("@ColumnType", false)){
                        map.put("@ColumnType", true);
                        packages.append("import tk.mybatis.mapper.annotation.ColumnType;\n");
                    }
                    SysEnumType sysEnumType = new SysEnumType();
                    sysEnumType.setColumnType(generateTable.getColumnType());
                    List<SysEnumType> sysEnumTypeList = sysEnumTypeService.select(sysEnumType);
                    if(CollectionUtils.isEmpty(sysEnumTypeList)){
                        throw new CommonException("字段 "+generateTable.getColumnName()+" 类型为 "+generateTable.getColumnType()+"，未在系统中定义此类型!");
                    }
                    sysEnumType = sysEnumTypeList.get(0);
                    try {
                        Class typeHandlerClass = Class.forName(sysEnumType.getTypeHandler());
                        if(!map.getOrDefault(typeHandlerClass.getSimpleName(), false)){
                            map.put(typeHandlerClass.getSimpleName(), true);
                            packages.append("import ").append(typeHandlerClass.getName()).append(";\n");
                        }
                        entityBody.append("\t@ColumnType(typeHandler = ").append(typeHandlerClass.getSimpleName()).append(".class)\n");
                    } catch (ClassNotFoundException e) {
                        throw new CommonException("未找到 "+sysEnumType.getTypeHandler()+" 类!");
                    }
                    try {
                        Class javaTypeClass = Class.forName(sysEnumType.getJavaType());
                        if(!map.getOrDefault(javaTypeClass.getSimpleName(), false)){
                            map.put(javaTypeClass.getSimpleName(), true);
                            packages.append("import ").append(javaTypeClass.getName()).append(";\n");
                        }
                        columnJavaType = javaTypeClass.getSimpleName();
                    } catch (ClassNotFoundException e) {
                        throw new CommonException("未找到 "+sysEnumType.getJavaType()+" 类!");
                    }
                    break;
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
                .append("import cn.org.ferry.system.dto.BaseDTO;\n")
                .append("import javax.persistence.Table;\n")
                .append(packages).append('\n')
                .append("/**\n * Generate by code generator\n")
                .append(" * ").append(Optional.ofNullable(sysGenerateTable.getTableComment()).orElse("")).append("\n")
                .append(" */\n\n")
                .append("@Table(name = \"").append(sysGenerateTable.getTableName()).append("\")\n")
                .append("public class ").append(sysGenerateTable.getEntityName().split("\\.")[0]).append(" extends BaseDTO {\n")
                .append(entityBody).append(getterAndSetter).append("}");
        return entityFile.toString();
    }
}
