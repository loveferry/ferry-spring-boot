package cn.org.ferry.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import javax.sql.DataSource;

/**
 * liquibase配置类
 */
@Configuration
public class LiquibaseConfiguration {
    /**
     * sys Liquibase
     */
    @Bean
    public SpringLiquibase sysLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:sys/master.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(true);
        liquibase.setResourceLoader(new DefaultResourceLoader());
        // 覆盖Liquibase changelog表名
        liquibase.setDatabaseChangeLogTable("changelog_table_sys");
        liquibase.setDatabaseChangeLogLockTable("changelog_lock_table_sys");
        return liquibase;
    }

    /**
     * doc Liquibase
     */
    @Bean
    public SpringLiquibase docLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:doc/master.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(true);
        liquibase.setResourceLoader(new DefaultResourceLoader());
        // 覆盖Liquibase changelog表名
        liquibase.setDatabaseChangeLogTable("changelog_table_doc");
        liquibase.setDatabaseChangeLogLockTable("changelog_lock_table_doc");
        return liquibase;
    }

    /**
     * log Liquibase
     */
    @Bean
    public SpringLiquibase logLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:log/master.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(true);
        liquibase.setResourceLoader(new DefaultResourceLoader());
        // 覆盖Liquibase changelog表名
        liquibase.setDatabaseChangeLogTable("changelog_table_log");
        liquibase.setDatabaseChangeLogLockTable("changelog_lock_table_log");
        return liquibase;
    }


}
