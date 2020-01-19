package cn.org.ferry.mybatis.providers;


import cn.org.ferry.mybatis.entity.Config;
import cn.org.ferry.mybatis.entity.EntityColumn;
import cn.org.ferry.mybatis.entity.EntityTable;
import cn.org.ferry.mybatis.exceptions.CommonMapperException;
import cn.org.ferry.mybatis.helpers.EntityHelper;
import cn.org.ferry.mybatis.helpers.MapperHelper;
import cn.org.ferry.mybatis.utils.MetaObjectUtil;
import cn.org.ferry.mybatis.utils.MsUtil;
import cn.org.ferry.mybatis.utils.StringUtil;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>通用Mapper模板类，扩展通用Mapper时需要继承该类
 *
 * @author ferry ferry_sy@163.com
 */

public abstract class MapperTemplate {
    private static final XMLLanguageDriver languageDriver = new XMLLanguageDriver();

    /**
     * 方法 hash 表
     */
    protected Map<String, Method> methodMap = new ConcurrentHashMap<>();


    protected Map<String, Class<?>> entityClassMap = new ConcurrentHashMap<>();

    /**
     * mapper 接口的 Class 对象
     */
    protected Class<?> mapperClass;

    /**
     * 通用 mapper 逻辑处理类
     */
    protected MapperHelper mapperHelper;

    public MapperTemplate(Class<?> mapperClass, MapperHelper mapperHelper) {
        this.mapperClass = mapperClass;
        this.mapperHelper = mapperHelper;
    }

    /**
     * 该方法仅仅用来初始化ProviderSqlSource
     */
    public String dynamicSQL(Object record) {
        return "dynamicSQL";
    }

    /**
     * 添加映射方法
     */
    public void addMethodMap(String methodName, Method method) {
        methodMap.put(methodName, method);
    }

    /**
     * 获取IDENTITY值的表达式
     */
    public String getIdentity(EntityColumn column) {
        return MessageFormat.format(mapperHelper.getConfig().getIdentity(), column.getColumn(), column.getProperty(), column.getTable().getName());
    }

    /**
     * 是否支持该通用方法
     */
    public boolean supportMethod(String msId) {
        Class<?> mapperClass = MsUtil.getMapperClass(msId);
        if (mapperClass != null && this.mapperClass.isAssignableFrom(mapperClass)) {
            String methodName = MsUtil.getMethodName(msId);
            return methodMap.get(methodName) != null;
        }
        return false;
    }

    /**
     * 设置返回值类型 - 为了让typeHandler在select时有效，改为设置resultMap
     */
    protected void setResultType(MappedStatement ms, Class<?> entityClass) {
        EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        List<ResultMap> resultMaps = new ArrayList<>();
        resultMaps.add(entityTable.getResultMap(ms.getConfiguration()));
        MetaObject metaObject = MetaObjectUtil.forObject(ms);
        metaObject.setValue("resultMaps", Collections.unmodifiableList(resultMaps));
    }

    /**
     * 重新设置SqlSource
     */
    protected void setSqlSource(MappedStatement ms, SqlSource sqlSource) {
        MetaObject msObject = MetaObjectUtil.forObject(ms);
        msObject.setValue("sqlSource", sqlSource);
    }

    /**
     * 通过xmlSql创建sqlSource
     */
    public SqlSource createSqlSource(MappedStatement ms, String xmlSql) {
        return languageDriver.createSqlSource(ms.getConfiguration(), "<script>\n\t" + xmlSql + "</script>", null);
    }

    /**
     * 获取返回值类型 - 实体类型
     */
    public Class<?> getEntityClass(MappedStatement ms) {
        String msId = ms.getId();
        if (entityClassMap.containsKey(msId)) {
            return entityClassMap.get(msId);
        } else {
            Class<?> mapperClass = MsUtil.getMapperClass(msId);
            Type[] types = mapperClass.getGenericInterfaces();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType t = (ParameterizedType) type;
                    if (t.getRawType() == this.mapperClass || this.mapperClass.isAssignableFrom((Class<?>) t.getRawType())) {
                        Class<?> returnType = (Class<?>) t.getActualTypeArguments()[0];
                        //获取该类型后，第一次对该类型进行初始化
                        EntityHelper.initEntityNameMap(returnType, mapperHelper.getConfig());
                        entityClassMap.put(msId, returnType);
                        return returnType;
                    }
                }
            }
        }
        throw new CommonMapperException("无法获取 " + msId + " 方法的泛型信息!");
    }

    /**
     * 获取实体类的表名
     */
    protected String tableName(Class<?> entityClass) {
        EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
        String prefix = entityTable.getPrefix();
        if (StringUtil.isEmpty(prefix)) {
            //使用全局配置
            prefix = mapperHelper.getConfig().getPrefix();
        }
        if (StringUtil.isNotEmpty(prefix)) {
            return prefix + "." + entityTable.getName();
        }
        return entityTable.getName();
    }

    public Config getConfig() {
        return mapperHelper.getConfig();
    }

    public String getIdentity() {
        return getConfig().getIdentity();
    }

    public boolean isBefore() {
        return getConfig().isBefore();
    }

    public boolean isCheckExampleEntityClass() {
        return getConfig().isCheckExampleEntityClass();
    }

    public boolean isNotEmpty() {
        return getConfig().isNotEmpty();
    }

    /**
     * 重新设置SqlSource
     */
    public void setSqlSource(MappedStatement ms) throws Exception {
        if (this.mapperClass == MsUtil.getMapperClass(ms.getId())) {
            throw new CommonMapperException("请不要配置或扫描通用Mapper接口类：" + this.mapperClass);
        }
        Method method = methodMap.get(MsUtil.getMethodName(ms));
        try {
            if (method.getReturnType() == Void.TYPE) {                              // 第一种，直接操作ms，不需要返回值
                method.invoke(this, ms);
            } else if (SqlNode.class.isAssignableFrom(method.getReturnType())) {    // 第二种，返回SqlNode
                SqlNode sqlNode = (SqlNode) method.invoke(this, ms);
                DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(ms.getConfiguration(), sqlNode);
                setSqlSource(ms, dynamicSqlSource);
            } else if (String.class.equals(method.getReturnType())) {               // 第三种，返回xml形式的sql字符串
                String xmlSql = (String) method.invoke(this, ms);
                SqlSource sqlSource = createSqlSource(ms, xmlSql);
                //替换原有的SqlSource
                setSqlSource(ms, sqlSource);
            } else {
                throw new CommonMapperException("自定义Mapper方法返回类型错误,可选的返回类型为void,SqlNode,String三种!");
            }
        } catch (IllegalAccessException e) {
            throw new CommonMapperException(e);
        } catch (InvocationTargetException e) {
            throw new CommonMapperException(e.getTargetException() != null ? e.getTargetException() : e);
        }
    }

    /**
     * 获取序列下个值的表达式
     */
    protected String getSeqNextVal(EntityColumn column) {
        if(StringUtil.isNotEmpty(column.getSequenceName())){
            return column.getSequenceName()+".nextval";
        }
        return MessageFormat.format(mapperHelper.getConfig().getSeqFormat(), (column.getTable().getName()+"_s").toUpperCase(), column.getColumn(), column.getProperty());
    }
}
