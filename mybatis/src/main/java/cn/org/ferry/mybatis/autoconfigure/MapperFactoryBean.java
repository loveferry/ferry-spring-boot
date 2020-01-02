package cn.org.ferry.mybatis.autoconfigure;

import cn.org.ferry.mybatis.helpers.MapperHelper;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import static org.springframework.util.Assert.notNull;

/**
 * mapper 接口的实现类
 * @see org.springframework.beans.factory.InitializingBean 实现该接口后，在初始化完成后调用 afterPropertiesSet 方法
 * @see FactoryBean 工厂Bean 实现该接口， getBean 获取到的不是该对象本身，而是由它产生的对象 T
 */

public class MapperFactoryBean<T> extends SqlSessionDaoSupport implements FactoryBean<T> {
    private Logger logger = LoggerFactory.getLogger(MapperFactoryBean.class);


    private Class<T> mapperInterface;

    private boolean addToConfig = true;

    private MapperHelper mapperHelper;

    public MapperFactoryBean() {
    }

    public MapperFactoryBean(Class<T> mapperInterface) {
        this();
        this.mapperInterface = mapperInterface;
    }

    /**
     * 初始化 mapper 后调用 afterPropertiesSet 方法
     * 在超类 DaoSupport 中的 afterPropertiesSet 方法中调用此方法
     * 在此方法中的 isExtendCommonMapper 判断是否继承通用 mapper, 若继承则注册相应的通用 mapper
     */
    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();

        notNull(this.mapperInterface, "Property 'mapperInterface' is required");

        Configuration configuration = getSqlSession().getConfiguration();
        if (this.addToConfig && !configuration.hasMapper(this.mapperInterface)) {
            try {
                configuration.addMapper(this.mapperInterface);
            } catch (Exception e) {
                logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", e);
                throw new IllegalArgumentException(e);
            } finally {
                ErrorContext.instance().reset();
            }
        }
        //直接针对接口处理通用接口方法对应的 MappedStatement 是安全的，通用方法不会出现 IncompleteElementException 的情况
        if (configuration.hasMapper(this.mapperInterface) && mapperHelper != null && mapperHelper.isExtendCommonMapper(this.mapperInterface)) {
            mapperHelper.processConfiguration(getSqlSession().getConfiguration(), this.mapperInterface);
        }
    }

    /**
     * Return the mapper interface of the MyBatis mapper
     *
     * @return class of the interface
     */
    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    /**
     * Sets the mapper interface of the MyBatis mapper
     *
     * @param mapperInterface class of the interface
     */
    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getObject() throws Exception {
        return getSqlSession().getMapper(this.mapperInterface);
    }

    //------------- mutators --------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<T> getObjectType() {
        return this.mapperInterface;
    }

    /**
     * Return the flag for addition into MyBatis config.
     *
     * @return true if the mapper will be added to MyBatis in the case it is not already
     * registered.
     */
    public boolean isAddToConfig() {
        return addToConfig;
    }

    /**
     * If addToConfig is false the mapper will not be added to MyBatis. This means
     * it must have been included in mybatis-config.xml.
     * <p/>
     * If it is true, the mapper will be added to MyBatis in the case it is not already
     * registered.
     * <p/>
     * By default addToCofig is true.
     *
     * @param addToConfig
     */
    public void setAddToConfig(boolean addToConfig) {
        this.addToConfig = addToConfig;
    }

    /**
     * 设置通用 Mapper 配置
     *
     * @param mapperHelper
     */
    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
