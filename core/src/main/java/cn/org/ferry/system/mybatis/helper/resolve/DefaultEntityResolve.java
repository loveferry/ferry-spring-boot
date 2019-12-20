package cn.org.ferry.system.mybatis.helper.resolve;

import cn.org.ferry.system.exception.MybatisException;
import cn.org.ferry.system.mybatis.annotation.ColumnType;
import cn.org.ferry.system.mybatis.annotation.KeySql;
import cn.org.ferry.system.mybatis.annotation.NameStyle;
import cn.org.ferry.system.mybatis.annotation.Order;
import cn.org.ferry.system.mybatis.code.IdentityDialect;
import cn.org.ferry.system.mybatis.code.ORDER;
import cn.org.ferry.system.mybatis.code.Style;
import cn.org.ferry.system.mybatis.entity.Config;
import cn.org.ferry.system.mybatis.entity.EntityColumn;
import cn.org.ferry.system.mybatis.entity.EntityField;
import cn.org.ferry.system.mybatis.entity.EntityTable;
import cn.org.ferry.system.mybatis.genid.GenId;
import cn.org.ferry.system.mybatis.gensql.GenSql;
import cn.org.ferry.system.mybatis.helper.FieldHelper;
import cn.org.ferry.system.mybatis.utils.SimpleTypeUtil;
import cn.org.ferry.system.mybatis.utils.SqlReservedWords;
import com.github.pagehelper.util.StringUtil;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.text.MessageFormat;
import java.util.LinkedHashSet;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author liuzh
 */
public class DefaultEntityResolve implements EntityResolve {
    private final Log log = LogFactory.getLog(DefaultEntityResolve.class);

    @Override
    public EntityTable resolveEntity(Class<?> entityClass, Config config) {
        Style style = config.getStyle();
        //style，该注解优先于全局配置
        if (entityClass.isAnnotationPresent(NameStyle.class)) {
            NameStyle nameStyle = entityClass.getAnnotation(NameStyle.class);
            style = nameStyle.value();
        }

        //创建并缓存EntityTable
        EntityTable entityTable = null;
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (!"".equals(table.name())) {
                entityTable = new EntityTable(entityClass);
                entityTable.setTable(table);
            }
        }
        if (entityTable == null) {
            entityTable = new EntityTable(entityClass);
            //可以通过stye控制
            String tableName = convertByStyle(entityClass.getSimpleName(), style);
            //自动处理关键字
            if (StringUtil.isNotEmpty(config.getWrapKeyword()) && SqlReservedWords.containsWord(tableName)) {
                tableName = MessageFormat.format(config.getWrapKeyword(), tableName);
            }
            entityTable.setName(tableName);
        }
        entityTable.setEntityClassColumns(new LinkedHashSet<EntityColumn>());
        entityTable.setEntityClassPKColumns(new LinkedHashSet<EntityColumn>());
        //处理所有列
        List<EntityField> fields = null;
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
            processField(entityTable, field, config, style);
        }
        //当pk.size=0的时候使用所有列作为主键
        if (entityTable.getEntityClassPKColumns().size() == 0) {
            entityTable.setEntityClassPKColumns(entityTable.getEntityClassColumns());
        }
        entityTable.initPropertyMap();
        return entityTable;
    }

    /**
     * 处理字段
     *
     * @param entityTable
     * @param field
     * @param config
     * @param style
     */
    protected void processField(EntityTable entityTable, EntityField field, Config config, Style style) {
        //排除字段
        if (field.isAnnotationPresent(Transient.class)) {
            return;
        }
        //Id
        EntityColumn entityColumn = new EntityColumn(entityTable);
        //是否使用 {xx, javaType=xxx}
        entityColumn.setUseJavaType(config.isUseJavaType());
        //记录 field 信息，方便后续扩展使用
        entityColumn.setEntityField(field);
        if (field.isAnnotationPresent(Id.class)) {
            entityColumn.setId(true);
        }
        //Column
        String columnName = null;
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            columnName = column.name();
            entityColumn.setUpdatable(column.updatable());
            entityColumn.setInsertable(column.insertable());
        }
        //ColumnType
        if (field.isAnnotationPresent(ColumnType.class)) {
            ColumnType columnType = field.getAnnotation(ColumnType.class);
            //是否为 blob 字段
            entityColumn.setBlob(columnType.isBlob());
            //column可以起到别名的作用
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
            columnName = convertByStyle(field.getName(), style);
        }
        //自动处理关键字
        if (StringUtil.isNotEmpty(config.getWrapKeyword()) && SqlReservedWords.containsWord(columnName)) {
            columnName = MessageFormat.format(config.getWrapKeyword(), columnName);
        }
        entityColumn.setProperty(field.getName());
        entityColumn.setColumn(columnName);
        entityColumn.setJavaType(field.getJavaType());
        if (field.getJavaType().isPrimitive()) {
            log.warn("通用 Mapper 警告信息: <[" + entityColumn + "]> 使用了基本类型，基本类型在动态 SQL 中由于存在默认值，因此任何时候都不等于 null，建议修改基本类型为对应的包装类型!");
        }
        //OrderBy
        processOrderBy(entityTable, field, entityColumn);
        //处理主键策略
        processKeyGenerator(entityTable, field, entityColumn);
        entityTable.getEntityClassColumns().add(entityColumn);
        if (entityColumn.isId()) {
            entityTable.getEntityClassPKColumns().add(entityColumn);
        }
    }

    /**
     * 处理排序
     *
     * @param entityTable
     * @param field
     * @param entityColumn
     */
    protected void processOrderBy(EntityTable entityTable, EntityField field, EntityColumn entityColumn) {
        String orderBy = "";
        if(field.isAnnotationPresent(OrderBy.class)){
            orderBy = field.getAnnotation(OrderBy.class).value();
            if ("".equals(orderBy)) {
                orderBy = "ASC";
            }
            log.warn(OrderBy.class + " is outdated, use " + Order.class + " instead!");
        }
        if (field.isAnnotationPresent(Order.class)) {
            Order order = field.getAnnotation(Order.class);
            if ("".equals(order.value()) && "".equals(orderBy)) {
                orderBy = "ASC";
            } else {
                orderBy = order.value();
            }
            entityColumn.setOrderPriority(order.priority());
        }
        if (StringUtil.isNotEmpty(orderBy)) {
            entityColumn.setOrderBy(orderBy);
        }
    }

    /**
     * 处理主键策略
     *
     * @param entityTable
     * @param field
     * @param entityColumn
     */
    protected void processKeyGenerator(EntityTable entityTable, EntityField field, EntityColumn entityColumn) {
        //KeySql 优先级最高
        if (field.isAnnotationPresent(KeySql.class)) {
            processKeySql(entityTable, entityColumn, field.getAnnotation(KeySql.class));
        } else if (field.isAnnotationPresent(GeneratedValue.class)) {
            //执行 sql - selectKey
            processGeneratedValue(entityTable, entityColumn, field.getAnnotation(GeneratedValue.class));
        }
    }

    /**
     * 处理 GeneratedValue 注解
     *
     * @param entityTable
     * @param entityColumn
     * @param generatedValue
     */
    protected void processGeneratedValue(EntityTable entityTable, EntityColumn entityColumn, GeneratedValue generatedValue) {
        if ("JDBC".equals(generatedValue.generator())) {
            entityColumn.setIdentity(true);
            entityColumn.setGenerator("JDBC");
            entityTable.setKeyProperties(entityColumn.getProperty());
            entityTable.setKeyColumns(entityColumn.getColumn());
        } else {
            //允许通过generator来设置获取id的sql,例如mysql=CALL IDENTITY(),hsqldb=SELECT SCOPE_IDENTITY()
            //允许通过拦截器参数设置公共的generator
            if (generatedValue.strategy() == GenerationType.IDENTITY) {
                //mysql的自动增长
                entityColumn.setIdentity(true);
                if (!"".equals(generatedValue.generator())) {
                    String generator = null;
                    IdentityDialect identityDialect = IdentityDialect.getDatabaseDialect(generatedValue.generator());
                    if (identityDialect != null) {
                        generator = identityDialect.getIdentityRetrievalStatement();
                    } else {
                        generator = generatedValue.generator();
                    }
                    entityColumn.setGenerator(generator);
                }
            } else {
                throw new MybatisException(entityColumn.getProperty()
                        + " - 该字段@GeneratedValue配置只允许以下几种形式:" +
                        "\n1.useGeneratedKeys的@GeneratedValue(generator=\\\"JDBC\\\")  " +
                        "\n2.类似mysql数据库的@GeneratedValue(strategy=GenerationType.IDENTITY[,generator=\"Mysql\"])");
            }
        }
    }

    /**
     * 处理 KeySql 注解
     *
     * @param entityTable
     * @param entityColumn
     * @param keySql
     */
    protected void processKeySql(EntityTable entityTable, EntityColumn entityColumn, KeySql keySql) {
        if (keySql.useGeneratedKeys()) {
            entityColumn.setIdentity(true);
            entityColumn.setGenerator("JDBC");
            entityTable.setKeyProperties(entityColumn.getProperty());
            entityTable.setKeyColumns(entityColumn.getColumn());
        } else if (keySql.dialect() == IdentityDialect.DEFAULT) {
            entityColumn.setIdentity(true);
            entityColumn.setOrder(ORDER.AFTER);
        }  else if (keySql.dialect() != IdentityDialect.NULL) {
            //自动增长
            entityColumn.setIdentity(true);
            entityColumn.setOrder(ORDER.AFTER);
            entityColumn.setGenerator(keySql.dialect().getIdentityRetrievalStatement());
        } else if (StringUtil.isNotEmpty(keySql.sql())){

            entityColumn.setIdentity(true);
            entityColumn.setOrder(keySql.order());
            entityColumn.setGenerator(keySql.sql());
        } else if (keySql.genSql() != GenSql.NULL.class){
            entityColumn.setIdentity(true);
            entityColumn.setOrder(keySql.order());
            try {
                GenSql genSql = keySql.genSql().newInstance();
                entityColumn.setGenerator(genSql.genSql(entityTable, entityColumn));
            } catch (Exception e) {
                log.error("实例化 GenSql 失败: " + e, e);
                throw new MybatisException("实例化 GenSql 失败: " + e, e);
            }
        } else if(keySql.genId() != GenId.NULL.class){
            entityColumn.setIdentity(false);
            entityColumn.setGenIdClass(keySql.genId());
        } else {
            throw new MybatisException(entityTable.getEntityClass().getCanonicalName()
                    + " 类中的 @KeySql 注解配置无效!");
        }
    }

    /**
     * 根据指定的样式进行转换
     *
     * @param str
     * @param style
     * @return
     */
    public static String convertByStyle(String str, Style style) {
        switch (style) {
            case camelhump:
                return camelhumpToUnderline(str);
            case uppercase:
                return str.toUpperCase();
            case lowercase:
                return str.toLowerCase();
            case camelhumpAndLowercase:
                return camelhumpToUnderline(str).toLowerCase();
            case camelhumpAndUppercase:
                return camelhumpToUnderline(str).toUpperCase();
            case normal:
            default:
                return str;
        }
    }

    /**
     * 将驼峰风格替换为下划线风格
     */
    public static String camelhumpToUnderline(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder(
                (size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isUppercaseAlpha(c)) {
                sb.append('_').append(toLowerAscii(c));
            } else {
                sb.append(c);
            }
        }
        return sb.charAt(0) == '_' ? sb.substring(1) : sb.toString();
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
