package cn.org.ferry.mybatis.entity;

import cn.org.ferry.mybatis.code.IdentityDialect;
import cn.org.ferry.mybatis.exceptions.CommonMapperException;
import cn.org.ferry.mybatis.resolve.EntityResolve;
import cn.org.ferry.mybatis.utils.SimpleTypeUtil;
import cn.org.ferry.mybatis.utils.StringUtil;
import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 通用Mapper属性配置
 *
 * @author liuzh
 */
public class Config {
    public static final String PREFIX = "mapper";

    private List<Class> mappers = new ArrayList<>();
    private String  identity;
    private boolean before;
    private String  seqFormat;
    private String  catalog;
    private String  schema;
    //校验调用Example方法时，Example(entityClass)和Mapper<EntityClass>是否一致
    private boolean checkExampleEntityClass;
    //使用简单类型
    //3.5.0 后默认值改为 true
    private boolean useSimpleType    = true;
    /**
     * @since 3.5.0
     */
    private boolean enumAsSimpleType;
    /**
     * 是否支持方法上的注解，默认false
     */
    private boolean enableMethodAnnotation;
    /**
     * 对于一般的getAllIfColumnNode，是否判断!=''，默认不判断
     */
    private boolean notEmpty;
    /**
     * 字段转换风格，默认驼峰转下划线
     */
    private CaseFormat caseFormat;
    /**
     * 处理关键字，默认空，mysql可以设置为 `{0}`, sqlserver 为 [{0}]，{0} 代表的列名
     */
    private String wrapKeyword = "";
    /**
     * 配置解析器
     */
    private Class<? extends EntityResolve> resolveClass;
    /**
     * 安全删除，开启后，不允许删全表，如 delete from table
     */
    private boolean safeDelete;
    /**
     * 安全更新，开启后，不允许更新全表，如 update table set xx=?
     */
    private boolean safeUpdate;
    /**
     * 是否设置 javaType
     */
    private boolean useJavaType;

    public String getCatalog() {
        return catalog;
    }

    /**
     * 设置全局的catalog,默认为空，如果设置了值，操作表时的sql会是catalog.tablename
     *
     * @param catalog
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    /**
     * 获取主键自增回写SQL
     */
    public String getIdentity() {
        if (StringUtil.isNotEmpty(identity)) {
            return identity;
        }
        //针对mysql的默认值
        return IdentityDialect.MYSQL.getIdentityRetrievalStatement();
    }

    /**
     * 主键自增回写方法,默认值MYSQL,详细说明请看文档
     */
    public void setIdentity(String identity) {
        IdentityDialect identityDialect = IdentityDialect.getDatabaseDialect(identity);
        if (identityDialect != null) {
            this.identity = identityDialect.getIdentityRetrievalStatement();
        } else {
            this.identity = identity;
        }
    }

    /**
     * 获取表前缀，带catalog或schema
     */
    public String getPrefix() {
        if (StringUtil.isNotEmpty(this.catalog)) {
            return this.catalog;
        }
        if (StringUtil.isNotEmpty(this.schema)) {
            return this.schema;
        }
        return "";
    }

    public String getSchema() {
        return schema;
    }

    /**
     * 设置全局的schema,默认为空，如果设置了值，操作表时的sql会是schema.tablename
     * <br>如果同时设置了catalog,优先使用catalog.tablename
     *
     * @param schema
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * 获取序列格式化模板
     *
     * @return
     */
    public String getSeqFormat() {
        if (StringUtil.isNotEmpty(this.seqFormat)) {
            return this.seqFormat;
        }
        return "{0}.nextval";
    }

    /**
     * 序列的获取规则,使用{num}格式化参数，默认值为{0}.nextval，针对Oracle
     * <br>可选参数一共3个，对应0,1,2,3分别为SequenceName，ColumnName, PropertyName，TableName
     *
     * @param seqFormat
     */
    public void setSeqFormat(String seqFormat) {
        this.seqFormat = seqFormat;
    }

    public CaseFormat getCaseFormat() {
        return this.caseFormat == null ? caseFormat.UPPER_UNDERSCORE : this.caseFormat;
    }

    public void setCaseFormat(CaseFormat caseFormat) {
        this.caseFormat = caseFormat;
    }

    public String getWrapKeyword() {
        return wrapKeyword;
    }

    public void setWrapKeyword(String wrapKeyword) {
        this.wrapKeyword = wrapKeyword;
    }

    /**
     * 获取SelectKey的Order
     */
    public boolean isBefore() {
        return before;
    }

    public void setBefore(boolean before) {
        this.before = before;
    }

    public boolean isCheckExampleEntityClass() {
        return checkExampleEntityClass;
    }

    public void setCheckExampleEntityClass(boolean checkExampleEntityClass) {
        this.checkExampleEntityClass = checkExampleEntityClass;
    }

    public boolean isEnableMethodAnnotation() {
        return enableMethodAnnotation;
    }

    public void setEnableMethodAnnotation(boolean enableMethodAnnotation) {
        this.enableMethodAnnotation = enableMethodAnnotation;
    }

    public boolean isEnumAsSimpleType() {
        return enumAsSimpleType;
    }

    public void setEnumAsSimpleType(boolean enumAsSimpleType) {
        this.enumAsSimpleType = enumAsSimpleType;
    }

    public boolean isNotEmpty() {
        return notEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        this.notEmpty = notEmpty;
    }

    public boolean isUseSimpleType() {
        return useSimpleType;
    }

    public void setUseSimpleType(boolean useSimpleType) {
        this.useSimpleType = useSimpleType;
    }

    /**
     * 主键自增回写方法执行顺序,默认AFTER,可选值为(BEFORE|AFTER)
     *
     * @param order
     */
    public void setOrder(String order) {
        this.before = "BEFORE".equalsIgnoreCase(order);
    }

    public List<Class> getMappers() {
        return mappers;
    }

    public void setMappers(List<Class> mappers) {
        this.mappers = mappers;
    }

    public Class<? extends EntityResolve> getResolveClass() {
        return resolveClass;
    }

    public void setResolveClass(Class<? extends EntityResolve> resolveClass) {
        this.resolveClass = resolveClass;
    }

    public boolean isSafeDelete() {
        return safeDelete;
    }

    public void setSafeDelete(boolean safeDelete) {
        this.safeDelete = safeDelete;
    }

    public boolean isSafeUpdate() {
        return safeUpdate;
    }

    public void setSafeUpdate(boolean safeUpdate) {
        this.safeUpdate = safeUpdate;
    }

    public boolean isUseJavaType() {
        return useJavaType;
    }

    public void setUseJavaType(boolean useJavaType) {
        this.useJavaType = useJavaType;
    }

    /**
     * 配置属性
     */
    public void setProperties(Properties properties) {
        if (properties == null) {
            //默认驼峰
            this.caseFormat = CaseFormat.UPPER_UNDERSCORE;
            return;
        }
        String identity = properties.getProperty("IDENTITY");
        if (StringUtil.isNotEmpty(identity)) {
            setIdentity(identity);
        }
        String seqFormat = properties.getProperty("seqFormat");
        if (StringUtil.isNotEmpty(seqFormat)) {
            setSeqFormat(seqFormat);
        }
        String catalog = properties.getProperty("catalog");
        if (StringUtil.isNotEmpty(catalog)) {
            setCatalog(catalog);
        }
        String schema = properties.getProperty("schema");
        if (StringUtil.isNotEmpty(schema)) {
            setSchema(schema);
        }

        //ORDER 有三个属性名可以进行配置
        String ORDER = properties.getProperty("ORDER");
        if (StringUtil.isNotEmpty(ORDER)) {
            setOrder(ORDER);
        }
        ORDER = properties.getProperty("order");
        if (StringUtil.isNotEmpty(ORDER)) {
            setOrder(ORDER);
        }
        ORDER = properties.getProperty("before");
        if (StringUtil.isNotEmpty(ORDER)) {
            setBefore(Boolean.valueOf(ORDER));
        }


        this.notEmpty = Boolean.valueOf(properties.getProperty("notEmpty"));
        this.enableMethodAnnotation = Boolean.valueOf(properties.getProperty("enableMethodAnnotation"));
        this.checkExampleEntityClass = Boolean.valueOf(properties.getProperty("checkExampleEntityClass"));
        //默认值 true，所以要特殊判断
        String useSimpleTypeStr = properties.getProperty("useSimpleType");
        if (StringUtil.isNotEmpty(useSimpleTypeStr)) {
            this.useSimpleType = Boolean.valueOf(useSimpleTypeStr);
        }
        this.enumAsSimpleType = Boolean.valueOf(properties.getProperty("enumAsSimpleType"));
        //注册新的基本类型，以逗号隔开，使用全限定类名
        String simpleTypes = properties.getProperty("simpleTypes");
        if (StringUtil.isNotEmpty(simpleTypes)) {
            SimpleTypeUtil.registerSimpleType(simpleTypes);
        }
        //使用 8 种基本类型
        if (Boolean.valueOf(properties.getProperty("usePrimitiveType"))) {
            SimpleTypeUtil.registerPrimitiveTypes();
        }
        String caseFormatStr = properties.getProperty("caseFormat");
        if (StringUtil.isNotEmpty(caseFormatStr)) {
            try {
                this.caseFormat = CaseFormat.valueOf(caseFormatStr);
            } catch (IllegalArgumentException e) {
                throw new CommonMapperException(caseFormatStr + "不是合法的CaseFormat值!");
            }
        } else {
            //默认驼峰
            this.caseFormat = CaseFormat.UPPER_UNDERSCORE;
        }
        //处理关键字
        String wrapKeyword = properties.getProperty("wrapKeyword");
        if (StringUtil.isNotEmpty(wrapKeyword)) {
            this.wrapKeyword = wrapKeyword;
        }
        //安全删除
        this.safeDelete = Boolean.valueOf(properties.getProperty("safeDelete"));
        //安全更新
        this.safeUpdate = Boolean.valueOf(properties.getProperty("safeUpdate"));
        //是否设置 javaType，true 时如 {id, javaType=java.lang.Long}
        this.useJavaType = Boolean.valueOf(properties.getProperty("useJavaType"));
    }
}
