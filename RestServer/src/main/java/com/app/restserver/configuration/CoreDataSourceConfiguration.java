package com.app.restserver.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableJpaRepositories(
        basePackages = ConfigurationUtil.JPA_CORE_REPOSITORY_BASE,
        entityManagerFactoryRef = ConfigurationUtil.JPA_CORE_ENTITY_MANAGER,
        transactionManagerRef = ConfigurationUtil.JPA_CORE_TRANSACTION_MANGER
)
public class CoreDataSourceConfiguration {
    private final Environment environment;

    @Autowired
    public CoreDataSourceConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "coreEntityManager")
    public LocalContainerEntityManagerFactoryBean coreEntityManager() {
        LocalContainerEntityManagerFactoryBean coreEntityManager = new LocalContainerEntityManagerFactoryBean();
        coreEntityManager.setDataSource(coreDataSource());
        coreEntityManager.setPackagesToScan(ConfigurationUtil.JPA_CORE_ENTITY_BASE);
        coreEntityManager.setJpaPropertyMap(getJpaPropertyMap());
        coreEntityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return coreEntityManager;
    }

    @Bean(name = "coreDataSource")
    public DataSource coreDataSource() {
        HikariConfig connectionPoolConfiguration = new HikariConfig();
        connectionPoolConfiguration.setDriverClassName("org.postgresql.Driver");
        connectionPoolConfiguration.setJdbcUrl(environment.getProperty("spring.datasource.core.url"));
        connectionPoolConfiguration.setPassword(environment.getProperty("spring.datasource.core.password"));
        connectionPoolConfiguration.setUsername(environment.getProperty("spring.datasource.core.username"));
        return new HikariDataSource(connectionPoolConfiguration);
    }

    private Map<String, String> getJpaPropertyMap() {
        Map<String, String> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.show_sql", "true");
        return jpaProperties;
    }

    @Bean(name = "coreTransactionManager")
    public PlatformTransactionManager coreTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(coreEntityManager().getObject());
        return transactionManager;
    }
}
