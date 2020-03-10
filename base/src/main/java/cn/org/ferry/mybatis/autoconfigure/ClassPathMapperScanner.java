package cn.org.ferry.mybatis.autoconfigure;

import cn.org.ferry.mybatis.annotations.RegisterMapper;
import cn.org.ferry.mybatis.code.IdentityDialect;
import cn.org.ferry.mybatis.entity.Config;
import cn.org.ferry.mybatis.helpers.MapperHelper;
import cn.org.ferry.mybatis.utils.SpringBootBindUtil;
import cn.org.ferry.mybatis.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import java.util.Arrays;
import java.util.Set;

/**
 * mybatis 的 mapper 扫描分析器，将指定包下面的类按一定规则过滤后以 BeanDefinition 形式注册到 spring 容器中
 *
 * @author ferry
 * @see MapperFactoryBean
 */

public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {
    private Logger logger = LoggerFactory.getLogger(ClassPathMapperScanner.class);

    private boolean addToConfig = true;

    private MapperHelper mapperHelper;

    private String mapperHelperBeanName;

    private MapperFactoryBean<?> mapperFactoryBean = new MapperFactoryBean<>();

    public ClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry, false);

        logger.info("Init ClassPathMapperScanner.");
    }

    /**
     * Configures parent scanner to search for the right interfaces. It can search
     * for all interfaces or just for those that extends a markerInterface or/and
     * those annotated with the annotationClass
     */
    public void registerFilters() {
        addIncludeFilter((metadataReader, metadataReaderFactory) -> {return true;});

        // exclude package-info.java
        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            if(className.endsWith("package-info")){
                return true;
            }
            return metadataReader.getAnnotationMetadata()
                    .hasAnnotation(RegisterMapper.class.getName());
        });

        logger.info("Add register Mybatis mapper filter rules.");
    }

    /**
     * Calls the parent search that will search and register all the candidates.
     * Then the registered objects are post processed to set them as
     * MapperFactoryBeans
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        logger.info("Scan basePackages {} for register Mybatis mapper.", Arrays.toString(basePackages));
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            logger.warn("No MyBatis mapper was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();

            logger.info("Creating MapperFactoryBean with name '" + holder.getBeanName() + "' and '" + definition.getBeanClassName() + "' mapperInterface");

            // the mapper interface is the original class of the bean
            // but, the actual class of the bean is MapperFactoryBean
            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName()); // issue #59
            definition.setBeanClass(this.mapperFactoryBean.getClass());
            //设置通用 Mapper
            if(StringUtils.hasText(this.mapperHelperBeanName)){
                definition.getPropertyValues().add("mapperHelper", new RuntimeBeanReference(this.mapperHelperBeanName));
            } else {
                //不做任何配置的时候使用默认方式
                if(this.mapperHelper == null){
                    this.mapperHelper = new MapperHelper();
                }
                definition.getPropertyValues().add("mapperHelper", this.mapperHelper);
            }

            definition.getPropertyValues().add("addToConfig", this.addToConfig);

            logger.info("Enabling autowire by type for MapperFactoryBean with name '" + holder.getBeanName() + "'.");
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            logger.warn("Skipping MapperFactoryBean with name '" + beanName
                    + "' and '" + beanDefinition.getBeanClassName() + "' mapperInterface"
                    + ". Bean already defined with the same name!");
            return false;
        }
    }

    public MapperHelper getMapperHelper() {
        return mapperHelper;
    }

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public void setAddToConfig(boolean addToConfig) {
        this.addToConfig = addToConfig;
    }

    /**
     * 配置通用 Mapper
     */
    public void setConfig(Config config) {
        if (mapperHelper == null) {
            mapperHelper = new MapperHelper();
        }
        mapperHelper.setConfig(config);
    }

    public void setMapperFactoryBean(MapperFactoryBean<?> mapperFactoryBean) {
        this.mapperFactoryBean = mapperFactoryBean != null ? mapperFactoryBean : new MapperFactoryBean<>();
    }

    public void setMapperHelperBeanName(String mapperHelperBeanName) {
        this.mapperHelperBeanName = mapperHelperBeanName;
    }

    /**
     * 从环境变量中获取 mapper 配置信息
     */
    public void setMapperProperties(Environment environment) {
        String jdbcUrl = environment.getProperty("spring.datasource.url");
        Config config = SpringBootBindUtil.bind(environment, Config.class, Config.PREFIX);
        if (mapperHelper == null) {
            mapperHelper = new MapperHelper();
        }
        if(config != null){
            mapperHelper.setConfig(config);
        }
        if(StringUtil.isNotEmpty(jdbcUrl)){
            String dbType = jdbcUrl.split(":")[1].toUpperCase();
            mapperHelper.getConfig().setIdentity(IdentityDialect.getDataBaseIdentityByDialect(dbType));
            mapperHelper.getConfig().setBefore(IdentityDialect.getDataBaseBeforeByDialect(dbType));
        }
    }
}
