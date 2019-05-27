package cn.org.ferry.system.config;

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
     *  sys Liquibase
     */
    @Bean
    public SpringLiquibase sysLiquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        // 用户模块Liquibase文件路径
        liquibase.setChangeLog("classpath:liquibase/sys/master.xml");
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(true);
        liquibase.setResourceLoader(new DefaultResourceLoader());
        // 覆盖Liquibase changelog表名
        liquibase.setDatabaseChangeLogTable("sys_changelog_table");
        liquibase.setDatabaseChangeLogLockTable("sys_changelog_lock_table");
        return liquibase;
    }
}
