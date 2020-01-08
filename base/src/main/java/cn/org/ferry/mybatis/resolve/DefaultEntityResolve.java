package cn.org.ferry.mybatis.resolve;

import cn.org.ferry.mybatis.annotations.ColumnType;
import cn.org.ferry.mybatis.code.IdentityDialect;
import cn.org.ferry.mybatis.entity.Config;
import cn.org.ferry.mybatis.entity.EntityColumn;
import cn.org.ferry.mybatis.entity.EntityField;
import cn.org.ferry.mybatis.entity.EntityTable;
import cn.org.ferry.mybatis.exceptions.CommonMapperException;
import cn.org.ferry.mybatis.helpers.FieldHelper;
import cn.org.ferry.mybatis.utils.SimpleTypeUtil;
import cn.org.ferry.mybatis.utils.SqlReservedWords;
import cn.org.ferry.mybatis.utils.StringUtil;
import com.google.common.base.CaseFormat;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 默认的实体类解析器
 */

public class DefaultEntityResolve implements EntityResolve {
    private static final Logger logger = LoggerFactory.getLogger(DefaultEntityResolve.class);

    /**
     * 解析实体类
     */
    @Override
    public EntityTable resolveEntity(Class<?> entityClass, Config config) {
        CaseFormat caseFormat = config.getCaseFormat();
        // caseFormat，该注解优先于全局配置
        if (entityClass.isAnnotationPresent(cn.org.ferry.mybatis.annotations.CaseFormat.class)) {
            caseFormat = entityClass.getAnnotation(cn.org.ferry.mybatis.annotations.CaseFormat.class).value();
        }

        // 创建并缓存EntityTable
        EntityTable entityTable = null;
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (StringUtil.isNotEmpty(table.name())) {
                entityTable = new EntityTable(entityClass);
                entityTable.setTable(table);
            }
        }
        if (entityTable == null) {
            entityTable = new EntityTable(entityClass);
            // 通过自定义的映射关系将实体类名转化成表名
            String tableName = convertByCaseFormat(entityClass.getSimpleName(), caseFormat);
            // 自动处理关键字
            if (StringUtil.isNotEmpty(config.getWrapKeyword()) && SqlReservedWords.containsWord(tableName)) {
                tableName = MessageFormat.format(config.getWrapKeyword(), tableName);
            }
            entityTable.setName(tableName);
        }
        entityTable.setEntityClassColumns(new LinkedHashSet<>());
        entityTable.setEntityClassPKColumns(new LinkedHashSet<>());
        //处理所有列
        List<EntityField> fields;
        if (config.isEnableMethodAnnotation()) {
            fields = FieldHelper.getAll(entityClass);
        } else {
            fields = FieldHelper.getFields(entityClass);
        }
        for (EntityField field : fields) {
            //如果启用了简单类型，就做简单类型校验，如果不是简单类型，直接跳过
            //3.5.0 如果启用了枚举作为简单类型，就不会自动忽略枚举类型
            //4.0 如果标记了 Column 或 ColumnType 注解，也不忽略
            if (config.isUseSimpleType()
                    && !field.isAnnotationPresent(Column.class)
                    && !field.isAnnotationPresent(ColumnType.class)
                    && !(SimpleTypeUtil.isSimpleType(field.getJavaType())
                    ||
                    (config.isEnumAsSimpleType() && Enum.class.isAssignableFrom(field.getJavaType())))) {
                continue;
            }
            processField(entityTable, field, config, caseFormat);
        }
        // 当pk.size=0的时候使用所有列作为主键
        if (entityTable.getEntityClassPKColumns().size() == 0) {
            entityTable.setEntityClassPKColumns(entityTable.getEntityClassColumns());
        }
        entityTable.initPropertyMap();
        return entityTable;
    }

    /**
     * 处理字段
     */
    private void processField(EntityTable entityTable, EntityField field, Config config, CaseFormat caseFormat) {
        // 排除字段
        if (field.isAnnotationPresent(Transient.class)) {
            return;
        }
        // Id
        EntityColumn entityColumn = new EntityColumn(entityTable);
        // 是否使用 {xx, javaType=xxx}
        entityColumn.setUseJavaType(config.isUseJavaType());
        // 记录 field 信息，方便后续扩展使用
        entityColumn.setEntityField(field);
        if (field.isAnnotationPresent(Id.class)) {
            entityColumn.setId(true);
        }
        // Column
        String columnName = null;
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            columnName = column.name();
            entityColumn.setUpdatable(column.updatable());
            entityColumn.setInsertable(column.insertable());
        }
        // ColumnType
        if (field.isAnnotationPresent(ColumnType.class)) {
            ColumnType columnType = field.getAnnotation(ColumnType.class);
            // 是否为 blob 字段
            entityColumn.setBlob(columnType.isBlob());
            // column可以起到别名的作用
            if (StringUtil.isEmpty(columnName) && StringUtil.isNotEmpty(columnType.column())) {
                columnName = columnType.column();
            }
            if (columnType.jdbcType() != JdbcType.UNDEFINED) {
                entityColumn.setJdbcType(columnType.jdbcType());
            }
            if (columnType.typeHandler() != UnknownTypeHandler.class) {
                entityColumn.setTypeHandler(columnType.typeHandler());
            }
        }
        //列名
        if (StringUtil.isEmpty(columnName)) {
            columnName = convertByCaseFormat(field.getName(), caseFormat);
        }
        //自动处理关键字
        if (StringUtil.isNotEmpty(config.getWrapKeyword()) && SqlReservedWords.containsWord(columnName)) {
            columnName = MessageFormat.format(config.getWrapKeyword(), columnName);
        }
        entityColumn.setProperty(field.getName());
        entityColumn.setColumn(columnName);
        entityColumn.setJavaType(field.getJavaType());
        if (field.getJavaType().isPrimitive()) {
            logger.warn("通用 Mapper 警告信息: <[" + entityColumn + "]> 使用了基本类型，基本类型在动态 SQL 中由于存在默认值，因此任何时候都不等于 null，建议修改基本类型为对应的包装类型!");
        }
        // OrderBy
        processOrderBy(field, entityColumn);
        // 处理主键策略
        processKeyGenerator(entityTable, field, entityColumn);
        entityTable.getEntityClassColumns().add(entityColumn);
        if (entityColumn.isId()) {
            entityTable.getEntityClassPKColumns().add(entityColumn);
        }
    }

    /**
     * 处理排序
     */
    private void processOrderBy(EntityField field, EntityColumn entityColumn) {
        String orderBy;
        if(field.isAnnotationPresent(OrderBy.class)){
            orderBy = field.getAnnotation(OrderBy.class).value();
            if ("".equals(orderBy)) {
                orderBy = "ASC";
            }
            entityColumn.setOrderBy(orderBy);
        }
    }

    /**
     * 处理主键策略
     */
    private void processKeyGenerator(EntityTable entityTable, EntityField field, EntityColumn entityColumn) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return ;
        }
        //执行 sql - selectKey
//        processGeneratedValue(entityTable, entityColumn, field.getAnnotation(GeneratedValue.class));


        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        String generator = generatedValue.generator();
        switch (generatedValue.strategy()){
            case SEQUENCE:
                entityColumn.setIdentity(true);
                entityColumn.setGenerator("SEQUENCE");
                if(field.isAnnotationPresent(SequenceGenerator.class)){
                    entityColumn.setSequenceName(field.getAnnotation(SequenceGenerator.class).sequenceName());
                }else{
                    entityColumn.setSequenceName(entityTable.getName()+"_S");
                }
                break;
            case IDENTITY:
                entityColumn.setIdentity(true);
                if (!"".equals(generator)) {
                    entityColumn.setGenerator(generator);
                }
                break;
            case AUTO:
                String upperCaseGenerator = generator.toUpperCase();
                switch (upperCaseGenerator){
                    case "JDBC":
                    case "SEQUENCE":
                        entityColumn.setIdentity(true);
                        entityColumn.setGenerator(upperCaseGenerator);
                        entityTable.setKeyProperties(entityColumn.getProperty());
                        entityTable.setKeyColumns(entityColumn.getColumn());
                        break;
                    default:
                        entityColumn.setIdentity(true);
                        entityColumn.setGenerator(null);
                        entityTable.setKeyProperties(entityColumn.getProperty());
                        entityTable.setKeyColumns(entityColumn.getColumn());
                }
                break;
            case TABLE:
            default:
                throw new CommonMapperException("通用 Mapper 不支持该主键生成策略："+generatedValue+" 实体类："+entityTable.getEntityClass());
        }


    }

    /**
     * 根据指定的样式进行转换
     */
    public static String convertByCaseFormat(String str, CaseFormat caseFormat) {
        return CaseFormat.LOWER_CAMEL.to(caseFormat, str);
    }

    public static boolean isUppercaseAlpha(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    public static char toLowerAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c += (char) 0x20;
        }
        return c;
    }
}
