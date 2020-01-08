package cn.org.ferry.mybatis.providers;


import cn.org.ferry.mybatis.entity.EntityColumn;
import cn.org.ferry.mybatis.exceptions.CommonMapperException;
import cn.org.ferry.mybatis.helpers.EntityHelper;
import cn.org.ferry.mybatis.helpers.MapperHelper;
import cn.org.ferry.mybatis.helpers.SqlHelper;
import cn.org.ferry.mybatis.utils.StringUtil;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>BaseInsertProvider实现类，基础方法实现类
 *
 * @author ferry ferry_sy@163.com
 */

public class BaseInsertProvider extends MapperTemplate {

    public BaseInsertProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String insert(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        processKey(sql, entityClass, ms);
        sql.append(SqlHelper.insertIntoTable(tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false));
        sql.append(SqlHelper.insertValuesColumns(entityClass, false));
        return sql.toString();
    }

    public String insertSelective(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        processKey(sql, entityClass, ms);
        sql.append(SqlHelper.insertIntoTable(tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, true));
        sql.append(SqlHelper.insertValuesColumns(entityClass, true));
        return sql.toString();
    }


    private void processKey(StringBuilder sql, Class<?> entityClass, MappedStatement ms){
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        // Identity列只能有一个
        boolean hasIdentityKey = false;
        // 先处理cache或bind节点
        for (EntityColumn column : columnList) {
            if (StringUtil.isNotEmpty(column.getSequenceName())) {
            } else if (column.isIdentity()) {
                // 这种情况下,如果原先的字段有值,需要先缓存起来,否则就一定会使用自动增长
                // 这是一个bind节点
                sql.append(SqlHelper.getBindCache(column.getProperty()));
                // 如果是Identity列，就需要插入selectKey
                // 如果已经存在Identity列，抛出异常
                if (hasIdentityKey) {
                    //jdbc类型只需要添加一次
                    if (column.getGenerator() != null && column.getGenerator().equals("JDBC")) {
                        continue;
                    }
                    throw new CommonMapperException(ms.getId() + "对应的实体类" + entityClass.getCanonicalName() + "中包含多个MySql的自动增长列,最多只能有一个!");
                }
                //插入selectKey
                newSelectKeyMappedStatement(ms, column);
                hasIdentityKey = true;
            } else if (column.isUuid()) {
                //uuid的情况，直接插入bind节点
                sql.append(SqlHelper.getBindValue(column, "@java.util.UUID@randomUUID().toString().replace(\"-\", \"\")"));
            }
        }
    }

    /**
     * 新建SelectKey节点
     */
    private void newSelectKeyMappedStatement(MappedStatement ms, EntityColumn column) {
        String keyId = ms.getId() + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        if (ms.getConfiguration().hasKeyGenerator(keyId)) {
            return;
        }
        Class<?> entityClass = getEntityClass(ms);
        //defaults
        Configuration configuration = ms.getConfiguration();
        KeyGenerator keyGenerator;
        boolean executeBefore = isBefore();
        String generator = column.getGenerator();
        String identity = StringUtil.isEmpty(generator) ? getIdentity() : generator;
        if ("JDBC".equals(identity)) {
            keyGenerator = new Jdbc3KeyGenerator();
        } else {
            if ("SEQUENCE".equals(identity)) {
                identity = "SELECT " + getSeqNextVal(column) + " FROM DUAL";
            }
            SqlSource sqlSource = new RawSqlSource(configuration, identity, entityClass);

            MappedStatement.Builder statementBuilder =
                    new MappedStatement.Builder(configuration, keyId, sqlSource, SqlCommandType.SELECT)
                    .resource(ms.getResource())
                    .fetchSize(null)
                    .statementType(StatementType.STATEMENT)
                    .keyGenerator(new NoKeyGenerator())
                    .keyProperty(column.getProperty())
                    .keyColumn(null)
                    .databaseId(null)
                    .lang(configuration.getDefaultScriptingLanguageInstance())
                    .resultOrdered(false)
                    .resultSets(null)
                    .timeout(configuration.getDefaultStatementTimeout())
                    .resultSetType(null)
                    .flushCacheRequired(false)
                    .useCache(false)
                    .cache(null);

            statementBuilder.parameterMap(
                    new ParameterMap.Builder(
                            configuration, statementBuilder.id() + "-Inline", entityClass, new ArrayList<>()
                    ).build()
            );
            List<ResultMap> resultMaps = new ArrayList<>();
            resultMaps.add(
                    new ResultMap.Builder(
                    configuration, statementBuilder.id() + "-Inline", column.getJavaType(), new ArrayList<>(), null
                    ).build()
            );
            statementBuilder.resultMaps(resultMaps);

            MappedStatement statement = statementBuilder.build();
            configuration.addMappedStatement(statement);
            keyGenerator = new SelectKeyGenerator(statement, executeBefore);
            configuration.addKeyGenerator(keyId, keyGenerator);
        }
        //keyGenerator
        try {
            MetaObject msObject = SystemMetaObject.forObject(ms);
            msObject.setValue("keyGenerator", keyGenerator);
            msObject.setValue("keyProperties", column.getTable().getKeyProperties());
            msObject.setValue("keyColumns", column.getTable().getKeyColumns());
        } catch (Exception e) {
            //ignore
        }
    }

}
