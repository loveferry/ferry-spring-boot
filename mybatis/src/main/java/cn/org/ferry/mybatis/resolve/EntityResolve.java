package cn.org.ferry.mybatis.resolve;

import cn.org.ferry.mybatis.entity.Config;
import cn.org.ferry.mybatis.entity.EntityTable;

/**
 * 解析实体类接口
 *
 * @author liuzh
 */
public interface EntityResolve {

    /**
     * 解析类为 EntityTable
     *
     * @param entityClass
     * @param config
     * @return
     */
    EntityTable resolveEntity(Class<?> entityClass, Config config);

}
