package cn.org.ferry.system.mybatis.helper.resolve;

import cn.org.ferry.system.mybatis.entity.Config;
import cn.org.ferry.system.mybatis.entity.EntityTable;

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
