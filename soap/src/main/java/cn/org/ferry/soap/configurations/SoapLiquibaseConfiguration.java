package cn.org.ferry.soap.configurations;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import javax.sql.DataSource;

/**
 * <p>liquibase soap 模块配置类
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/01/04 11:22
 */

@Configuration
@ConditionalOnExpression("${ferry.liquibase.enabled:true}")
public class SoapLiquibaseConfiguration {
    /**
     * soap Liquibase
     */
    @Bean
    public SpringLiquibase soapLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:cn/org/ferry/soap/liquibase/master.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(true);
        liquibase.setResourceLoader(new DefaultResourceLoader());
        // 覆盖Liquibase changelog表名
        liquibase.setDatabaseChangeLogTable("changelog_table");
        liquibase.setDatabaseChangeLogLockTable("changelog_lock_table");
        return liquibase;
    }
}
