package cn.org.ferry.mybatis.utils;

import cn.org.ferry.mybatis.entity.EntityColumn;
import cn.org.ferry.mybatis.exceptions.CommonMapperException;
import cn.org.ferry.mybatis.helpers.EntityHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * OGNL静态方法
 *
 * @author liuzh
 */
public abstract class OGNL {
    public static final String SAFE_DELETE_ERROR = "通用 Mapper 安全检查: 对查询条件参数进行检查时出错!";
    public static final String SAFE_DELETE_EXCEPTION = "通用 Mapper 安全检查: 当前操作的方法没有指定查询条件，不允许执行该操作!";


    /**
     * 检查 paremeter 对象中指定的 fields 是否全是 null，如果是则抛出异常
     *
     * @param parameter
     * @param fields
     * @return
     */
    public static boolean notAllNullParameterCheck(Object parameter, String fields) {
        if (parameter != null) {
            try {
                Set<EntityColumn> columns = EntityHelper.getColumns(parameter.getClass());
                Set<String> fieldSet = new HashSet<>(Arrays.asList(fields.split(",")));
                for (EntityColumn column : columns) {
                    if (fieldSet.contains(column.getProperty())) {
                        Object value = column.getEntityField().getValue(parameter);
                        if (value != null) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                throw new CommonMapperException(SAFE_DELETE_ERROR, e);
            }
        }
        throw new CommonMapperException(SAFE_DELETE_EXCEPTION);
    }

}
