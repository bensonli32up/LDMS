package ldms.technical.challenge.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import liquibase.integration.spring.SpringLiquibase;

/**
 * Configuration class for setting up Liquibase.
 */
@Configuration
public class LiquibaseConfig {
    @Bean
    public SpringLiquibase liquibase(final DataSource dataSource) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:/db/changelog/db.changelog-master.yaml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
