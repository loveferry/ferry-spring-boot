package cn.org.ferry.mybatis.gensql;


import cn.org.ferry.mybatis.entity.EntityColumn;
import cn.org.ferry.mybatis.entity.EntityTable;

/**
 * 生成 SQL，初始化时执行
 *
 * @author ferry
 */
public interface GenSql {

    String genSql(EntityTable entityTable, EntityColumn entityColumn);

    class NULL implements GenSql {
        @Override
        public String genSql(EntityTable entityTable, EntityColumn entityColumn) {
            throw new UnsupportedOperationException();
        }
    }
}
